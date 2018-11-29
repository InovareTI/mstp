function map_init_manual(){
 mymap.remove();
 
 mymap = L.map('mapid').setView([51.505, -0.09],4);
			 L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=sk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2ppYjhwaXo5MWY4NjNrcXF2bWhxNjg2byJ9.QM1xoBAe6s_kvZidmwE98A', {
				    attribution: 'MSTP by Inovare TI',
				    maxZoom: 18,
				    id: 'MSTP.WEB'
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
function load_site_markers_mapa_central(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"10"
			 
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: carregasitesmapaprincipal
		});
	function carregasitesmapaprincipal(data) {
		var aux=JSON.parse(data);
		
		map.addSource('VIVO', {
		    type: 'geojson',
		    data: aux.VIVO
		});
		map.addSource('TIM', {
		    type: 'geojson',
		    data: aux.TIM
		});
		map.addSource('CLARO', {
		    type: 'geojson',
		    data: aux.CLARO
		});
		map.addSource('USUARIOS', {
		    type: 'geojson',
		    data: aux.USUARIOS
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
			map.on('click', 'VIVO', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Site_Operadora + ": " + e.features[0].properties.Site_Id;
		        
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'TIM', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Site_Operadora + ": " + e.features[0].properties.Site_Id;
		       
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'CLARO', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.Site_Operadora + ": " + e.features[0].properties.Site_Id;
		        
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora)
		            .addTo(map);
		        map.zoomTo(19, {duration: 9000});
		        map.flyTo({center: e.features[0].geometry.coordinates});
		        
		    });
			map.on('click', 'USUARIOS', function (e) {
		        var coordinates = e.features[0].geometry.coordinates.slice();
		        var operadora = e.features[0].properties.usuario + ": " + e.features[0].properties.horario;
		        map.zoomTo(19, {duration: 9000});
		        // Ensure that if the map is zoomed out such that multiple
		        // copies of the feature are visible, the popup appears
		        // over the copy being pointed to.
		        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
		            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
		        }

		        new mapboxgl.Popup()
		            .setLngLat(coordinates)
		            .setHTML(operadora)
		            .addTo(map);
		        
		        map.flyTo({center: e.features[0].geometry.coordinates});
		       
		    });
			map.on('mouseenter', 'TIM', function () {
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
			map.on('mouseleave', 'VIVO', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseleave', 'CLARO', function () {
		        map.getCanvas().style.cursor = '';
		    });
			map.on('mouseleave', 'USUARIOS', function () {
		        map.getCanvas().style.cursor = '';
		    });
		$('#mapa_info').html('Sites Carregados');
	}
	}