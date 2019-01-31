function map_init_manual(){
 mymap.remove();
 
 mymap = L.map('mapid').setView([51.505, -0.09],4);
			 L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2pjMjYxYzFoMGludzJxczR1ZWp2aTBpaiJ9.KptnIvfz_61BmkgGIR_ZWA', {
				    attribution: 'MSTP by Inovare TI',
				    maxZoom: 18,
				    id: 'MSTP.WEB',
				    accessToken: 'pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2pjMjYxYzFoMGludzJxczR1ZWp2aTBpaiJ9.KptnIvfz_61BmkgGIR_ZWA'
				}).addTo(mymap);
}
function load_site_markers(){
	
	$.getJSON('./SiteMgmt?opt=3', function(data) {
		
		//alert(data);
		var geojsonLayer = new L.GeoJSON(null, {
		      onEachFeature: function (feature, layer) {
		        if (feature.properties) {
		          var popupString = '<div class="popup">';
		          for (var k in feature.properties) {
		            var v = feature.properties[k];
		            popupString += k + ': ' + v + '<br />';
		          }
		          popupString += '</div>';
		          layer.bindPopup(popupString, {
		            maxHeight: 200
		          });
		        }
		      }
		    });

		mymap.addLayer(geojsonLayer);
		geojsonLayer.clearLayers();
		geojsonLayer.addData(data);
		mymap.fitBounds(geojsonLayer.getBounds());
	});
	}
function load_site_markers_mapa_central_rollout(){
	alert("chegou na função do rollout");
	alert(sessionStorage.getItem("rollout_map_filtro"));
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"16",
 			  "filtros":sessionStorage.getItem("rollout_map_filtro")
 			},		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./RolloutServlet",
 		  cache: false,
 		  dataType: "text",
 		  success:load_mapa_rollout
 		});
	function load_mapa_rollout(data){
		
		data=JSON.parse(data);
		console.log(data);
		console.log(data.rollout);
		map.addSource('Rollout', {
		    type: 'geojson',
		    data: data.rollout,
		    cluster: true,
	        clusterMaxZoom: 14, // Max zoom to cluster points on
	        clusterRadius: 50
		});
		map.addLayer({
			"id": 'Rollout',
	        "type": "symbol",
	        "source": 'Rollout',
	        "layout": {
                "icon-image": "tower4"
	        }
		});
		map.addLayer({
	        id: "clusters_rollout",
	        type: "circle",
	        source: "Rollout",
	        filter: ["has", "point_count"],
	        paint: {
	            // Use step expressions (https://www.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
	            // with three steps to implement three types of circles:
	            //   * Blue, 20px circles when point count is less than 100
	            //   * Yellow, 30px circles when point count is between 100 and 750
	            //   * Pink, 40px circles when point count is greater than or equal to 750
	            "circle-color": [
	                "step",
	                ["get", "point_count"],
	                "#9999ff",
	                100,
	                "#7f7fff",
	                750,
	                "#6666ff"
	            ],
	            "circle-radius": [
	                "step",
	                ["get", "point_count"],
	                20,
	                100,
	                30,
	                750,
	                40
	            ]
	        }
	    });
		map.addLayer({
	        id: "cluster-count_Rollout",
	        type: "symbol",
	        source: "Rollout",
	        filter: ["has", "point_count"],
	        layout: {
	            "text-field": "{point_count_abbreviated}",
	            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
	            "text-size": 14
	        }
	    });
	}
}
function load_site_markers_mapa_central(){
	$.getJSON('./SiteMgmt?opt=10', function(data) {
		
		map.addSource('VIVO', {
		    type: 'geojson',
		    data: data.VIVO,
		    cluster: true,
	        clusterMaxZoom: 14, // Max zoom to cluster points on
	        clusterRadius: 50
		});
		
		map.addSource('NEXTEL', {
		    type: 'geojson',
		    data: data.NEXTEL,
		    cluster: true,
	        clusterMaxZoom: 14, // Max zoom to cluster points on
	        clusterRadius: 50
		});
		
		map.addSource('TIM', {
		    type: 'geojson',
		    data: data.TIM,
		    cluster: true,
	        clusterMaxZoom: 14, // Max zoom to cluster points on
	        clusterRadius: 50
		});
		
		map.addSource('CLARO', {
		    type: 'geojson',
		    data: data.CLARO,
		    cluster: true,
	        clusterMaxZoom: 14, // Max zoom to cluster points on
	        clusterRadius: 50
		});
		
		map.addSource('USUARIOS', {
		    type: 'geojson',
		    data: data.USUARIOS,
		    cluster: true,
	        clusterMaxZoom: 8, // Max zoom to cluster points on
	        clusterRadius: 50
		});
		
			map.addLayer({
				"id": 'VIVO',
		        "type": "symbol",
		        "source": 'VIVO',
		        "layout": {
	                "icon-image": "tower4"
		        }
			});
			map.addLayer({
				"id": 'NEXTEL',
		        "type": "symbol",
		        "source": 'NEXTEL',
		        "layout": {
	                "icon-image": "tower4"
		        }
			});
			
			map.addLayer({
				"id": 'TIM',
		        "type": "symbol",
		        "source": 'TIM',
		        "layout": {
	                "icon-image": "tower5"
		        }
			});
		
			
			map.addLayer({
				"id": 'CLARO',
		        "type": "symbol",
		        "source": 'CLARO',
		        "layout": {
	                "icon-image": "tower3"
		        }
			});
			map.addLayer({
				"id": 'USUARIOS',
		        "type": "symbol",
		        "source": 'USUARIOS',
		        "layout": {
	                "icon-image": "user1"
		        }
			});
			
			map.addLayer({
		        id: "clusters_TIM",
		        type: "circle",
		        source: "TIM",
		        filter: ["has", "point_count"],
		        paint: {
		            // Use step expressions (https://www.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
		            // with three steps to implement three types of circles:
		            //   * Blue, 20px circles when point count is less than 100
		            //   * Yellow, 30px circles when point count is between 100 and 750
		            //   * Pink, 40px circles when point count is greater than or equal to 750
		            "circle-color": [
		                "step",
		                ["get", "point_count"],
		                "#9999ff",
		                100,
		                "#7f7fff",
		                750,
		                "#6666ff"
		            ],
		            "circle-radius": [
		                "step",
		                ["get", "point_count"],
		                20,
		                100,
		                30,
		                750,
		                40
		            ]
		        }
		    });
			map.addLayer({
		        id: "clusters_NEXTEL",
		        type: "circle",
		        source: "NEXTEL",
		        filter: ["has", "point_count"],
		        paint: {
		            // Use step expressions (https://www.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
		            // with three steps to implement three types of circles:
		            //   * Blue, 20px circles when point count is less than 100
		            //   * Yellow, 30px circles when point count is between 100 and 750
		            //   * Pink, 40px circles when point count is greater than or equal to 750
		            "circle-color": [
		                "step",
		                ["get", "point_count"],
		                "#87CEEB",
		                100,
		                "#87CEEB",
		                750,
		                "#87CEFA"
		            ],
		            "circle-radius": [
		                "step",
		                ["get", "point_count"],
		                20,
		                100,
		                30,
		                750,
		                40
		            ]
		        }
		    });
			map.addLayer({
		        id: "clusters_CLARO",
		        type: "circle",
		        source: "CLARO",
		        filter: ["has", "point_count"],
		        paint: {
		            // Use step expressions (https://www.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
		            // with three steps to implement three types of circles:
		            //   * Blue, 20px circles when point count is less than 100
		            //   * Yellow, 30px circles when point count is between 100 and 750
		            //   * Pink, 40px circles when point count is greater than or equal to 750
		            "circle-color": [
		                "step",
		                ["get", "point_count"],
		                "#FF7F50",
		                100,
		                "#FF6347",
		                750,
		                "#FF0000"
		            ],
		            "circle-radius": [
		                "step",
		                ["get", "point_count"],
		                20,
		                100,
		                30,
		                750,
		                40
		            ]
		        }
		    });
			
			map.addLayer({
		        id: "clusters_VIVO",
		        type: "circle",
		        source: "VIVO",
		        filter: ["has", "point_count"],
		        paint: {
		            // Use step expressions (https://www.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
		            // with three steps to implement three types of circles:
		            //   * Blue, 20px circles when point count is less than 100
		            //   * Yellow, 30px circles when point count is between 100 and 750
		            //   * Pink, 40px circles when point count is greater than or equal to 750
		            "circle-color": [
		                "step",
		                ["get", "point_count"],
		                "#9400D3",
		                100,
		                "#9932CC",
		                750,
		                "#BA55D3"
		            ],
		            "circle-radius": [
		                "step",
		                ["get", "point_count"],
		                20,
		                100,
		                30,
		                750,
		                40
		            ]
		        }
		    });
			map.addLayer({
		        id: "clusters_USUARIOS",
		        type: "circle",
		        
		        source: "USUARIOS",
		        filter: ["has", "point_count"],
		        paint: {
		            // Use step expressions (https://www.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
		            // with three steps to implement three types of circles:
		            //   * Blue, 20px circles when point count is less than 100
		            //   * Yellow, 30px circles when point count is between 100 and 750
		            //   * Pink, 40px circles when point count is greater than or equal to 750
		            "circle-color": [
		                "step",
		                ["get", "point_count"],
		                "#A9A9A9",
		                100,
		                "#808080",
		                750,
		                "#696969"
		            ],
		            "circle-radius": [
		                "step",
		                ["get", "point_count"],
		                20,
		                100,
		                30,
		                750,
		                40
		            ]
		        }
		    });
			map.addLayer({
		        id: "cluster-count_VIVO",
		        type: "symbol",
		        source: "VIVO",
		        filter: ["has", "point_count"],
		        layout: {
		            "text-field": "{point_count_abbreviated}",
		            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
		            "text-size": 14
		        }
		    });
			map.addLayer({
		        id: "cluster-count_NEXTEL",
		        type: "symbol",
		        source: "NEXTEL",
		        filter: ["has", "point_count"],
		        layout: {
		            "text-field": "{point_count_abbreviated}",
		            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
		            "text-size": 14
		        }
		    });
			map.addLayer({
		        id: "cluster-count_TIM",
		        type: "symbol",
		        source: "TIM",
		        filter: ["has", "point_count"],
		        layout: {
		            "text-field": "{point_count_abbreviated}",
		            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
		            "text-size": 14
		        }
		    });
			map.addLayer({
		        id: "cluster-count_CLARO",
		        type: "symbol",
		        source: "CLARO",
		        filter: ["has", "point_count"],
		        layout: {
		            "text-field": "{point_count_abbreviated}",
		            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
		            "text-size": 14
		        }
		    });
			map.addLayer({
		        id: "cluster-count_USUARIOS",
		        type: "symbol",
		        source: "USUARIOS",
		        filter: ["has", "point_count"],
		        layout: {
		            "text-field": "{point_count_abbreviated}",
		            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
		            "text-size": 14
		        }
		    });
			
			map.on('click', 'VIVO', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Operadora + ": " + e.features[0].properties.SiteID;
		        
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora+'<br>'+coordinates)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'TIM', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Operadora + ": " + e.features[0].properties.SiteID;
		       
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora+'<br>'+coordinates)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'CLARO', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Operadora + ": " + e.features[0].properties.SiteID;
		        
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora+'<br>'+coordinates)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'NEXTEL', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Operadora + ": " + e.features[0].properties.SiteID;
		        
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora+'<br>'+coordinates)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'USUARIOS', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Usuario + ": " + e.features[0].properties.Data;
		        map.zoomTo(19, {duration: 9000});
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora+'<br>'+coordinates)
		            .addTo(map);
		        
		        map.flyTo({center: e.features[0].geometry.coordinates});
		       
		    });
			 // inspect a cluster on click
		    map.on('click', 'clusters_VIVO', function (e) {
		        var features = map.queryRenderedFeatures(e.point, { layers: ['clusters_VIVO'] });
		        var clusterId = features[0].properties.cluster_id;
		        map.getSource('VIVO').getClusterExpansionZoom(clusterId, function (err, zoom) {
		            if (err){
		            	alert("erro aqui");
		                return;
		            }
		            map.easeTo({
		                center: features[0].geometry.coordinates,
		                zoom: zoom
		            });
		        });
		    });
		    map.on('click', 'clusters_NEXTEL', function (e) {
		        var features = map.queryRenderedFeatures(e.point, { layers: ['clusters_NEXTEL'] });
		        var clusterId = features[0].properties.cluster_id;
		        map.getSource('NEXTEL').getClusterExpansionZoom(clusterId, function (err, zoom) {
		            if (err){
		            	alert("erro aqui");
		                return;
		            }

		            map.easeTo({
		                center: features[0].geometry.coordinates,
		                zoom: zoom
		            });
		        });
		    });
		    map.on('click', 'clusters_CLARO', function (e) {
		        var features = map.queryRenderedFeatures(e.point, { layers: ['clusters_CLARO'] });
		        var clusterId = features[0].properties.cluster_id;
		        map.getSource('CLARO').getClusterExpansionZoom(clusterId, function (err, zoom) {
		            if (err){
		            	alert("erro aqui");
		                return;
		            }

		            map.easeTo({
		                center: features[0].geometry.coordinates,
		                zoom: zoom
		            });
		        });
		    });
		    map.on('click', 'clusters_TIM', function (e) {
		        var features = map.queryRenderedFeatures(e.point, { layers: ['clusters_TIM'] });
		        var clusterId = features[0].properties.cluster_id;
		        map.getSource('TIM').getClusterExpansionZoom(clusterId, function (err, zoom) {
		            if (err){
		            	alert("erro aqui");
		                return;
		            }

		            map.easeTo({
		                center: features[0].geometry.coordinates,
		                zoom: zoom
		            });
		        });
		    });
		    map.on('click', 'clusters_USUARIOS', function (e) {
		        var features = map.queryRenderedFeatures(e.point, { layers: ['clusters_USUARIOS'] });
		        var clusterId = features[0].properties.cluster_id;
		        map.getSource('USUARIOS').getClusterExpansionZoom(clusterId, function (err, zoom) {
		            if (err){
		            	alert("erro aqui");
		                return;
		            }

		            map.easeTo({
		                center: features[0].geometry.coordinates,
		                zoom: zoom
		            });
		        });
		    });
			map.on('mouseenter', 'TIM', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
			map.on('mouseenter', 'NEXTEL', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
			map.on('mouseenter', 'VIVO', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
			map.on('mouseenter', 'CLARO', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
			map.on('mouseenter', 'USUARIOS', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
			map.on('mouseleave', 'TIM', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseleave', 'NEXTEL', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseleave', 'VIVO', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseleave', 'CLARO', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseleave', 'USUARIOS', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseenter', 'clusters_VIVO', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
			 map.on('mouseleave', 'clusters_VIVO', function () {
			        map.getCanvas().style.cursor = '';
			    });
			 map.on('mouseenter', 'clusters_TIM', function () {
			        map.getCanvas().style.cursor = 'pointer';
			    });
			 map.on('mouseleave', 'clusters_TIM', function () {
				        map.getCanvas().style.cursor = '';
				});
			 map.on('mouseenter', 'clusters_CLARO', function () {
			        map.getCanvas().style.cursor = 'pointer';
			    });
		   map.on('mouseleave', 'clusters_CLARO', function () {
				        map.getCanvas().style.cursor = '';
				    });
		   map.on('mouseenter', 'clusters_USUARIOS', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
		map.on('mouseleave', 'clusters_USUARIOS', function () {
			        map.getCanvas().style.cursor = '';
			    });
		 map.on('mouseenter', 'clusters_NEXTEL', function () {
		        map.getCanvas().style.cursor = 'pointer';
		    });
		map.on('mouseleave', 'clusters_NEXTEL', function () {
			        map.getCanvas().style.cursor = '';
			    });
		
		
		
		$('#mapa_info').html('Sites Carregados');
		//alert("Todos os dados carregados");
	});
	
	}