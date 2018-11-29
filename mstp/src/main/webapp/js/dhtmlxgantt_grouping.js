gantt.plugin(function(t) {
    t._groups = {
        relation_property: null,
        relation_id_property: "$group_id",
        group_id: null,
        group_text: null,
        loading: !1,
        loaded: 0,
        init: function(t) {
            var e = this;
            t.attachEvent("onClear", function() {
                e.clear()
            }), e.clear();
            var i = t._get_parent_id;
            t._get_parent_id = function(n) {
                return e.is_active() ? e.get_parent(t, n) : i.apply(this, arguments)
            };
            var n = t.setParent;
            t.setParent = function(i, a) {
                if (!e.is_active())
                    return n.apply(this, arguments);
                if (t.isTaskExists(a)) {
                    var s = t.getTask(a);
                    i[e.relation_property] = s[e.relation_id_property];
                }
            }, t.attachEvent("onBeforeTaskDisplay", function(i, n) {
                return e.is_active() && n.type == t.config.types.project&&!n.$virtual?!1 : !0
            }), t.attachEvent("onBeforeParse", function() {
                e.loading=!0
            }), t.attachEvent("onTaskLoading", function() {
                return e.is_active() && (e.loaded--, e.loaded <= 0 && (e.loading=!1, t.eachTask(t.bind(function(e) {
                    this.get_parent(t, e)
                }, e)), t._sync_order())), !0
            }), t.attachEvent("onParse", function() {
                e.loading=!1, e.loaded = 0
            })
        },
        get_parent: function(t, e, i) {
            var n = e[this.relation_property];
            if (void 0 !== this._groups_pull[n])
                return this._groups_pull[n];
            var a = t.config.root_id;
            return this.loading || (a = this.find_parent(i || t.getTaskByTime(), n, this.relation_id_property, t.config.root_id), this._groups_pull[n] = a), a
        },
        find_parent: function(t, e, i, n) {
            for (var a = 0; a < t.length; a++) {
                var s = t[a];
                if (void 0 !== s[i] && s[i] == e)
                    return s.id
            }
            return n
        },
        clear: function() {
            this._groups_pull = {}, this.relation_property = null, this.group_id = null, this.group_text = null
        },
        is_active: function() {
            return !!this.relation_property
        },
        generate_sections: function(e, i) {
            for (var n = [], a = 0; a < e.length; a++) {
                var s = t.copy(e[a]);
                s.type = i, s.open=!0, s.$virtual=!0, s.readonly=!0, s[this.relation_id_property] = s[this.group_id], s.text = s[this.group_text], n.push(s)
            }
            return n
        },
        clear_temp_tasks: function(t) {
            for (var e = 0; e < t.length; e++)
                t[e].$virtual && (t.splice(e, 1), e--)
        },
        generate_data: function(t, e) {
            var i = t.getLinks(), n = t.getTaskByTime();
            this.clear_temp_tasks(n);
            var a = [];
            this.is_active() && e && e.length && (a = this.generate_sections(e, t.config.types.project));
            var s = {
                links: i
            };
            return s.data = a.concat(n), s
        },
        update_settings: function(t, e, i) {
            this.clear(),
            this.relation_property = t, this.group_id = e, this.group_text = i
        },
        group_tasks: function(t, e, i, n, a) {
            this.update_settings(i, n, a);
            var s = this.generate_data(t, e);
            this.loaded = s.data.length, t._clear_data(), t.parse(s)
        }
    }, t._groups.init(t), t.groupBy = function(t) {
        t = t || {};
        var e = t.groups || null, i = t.relation_property || null, n = t.group_id || "key", a = t.group_text || "label";
        this._groups.group_tasks(this, e, i, n, a)
    }
});