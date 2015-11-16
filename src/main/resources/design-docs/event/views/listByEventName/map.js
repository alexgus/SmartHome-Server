function(doc) {
	if(doc.$table == "fr.utbm.to52.smarthome.controller.events.StorableEvent"){
		emit([doc.date,doc.eventName], doc);
	}
}