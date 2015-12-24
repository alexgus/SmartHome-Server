function(doc) {
	if(doc.$table == "fr.utbm.to52.smarthome.controller.events.core.StorableEvent"){
		emit([doc.date,doc.eventName], doc);
	}
}