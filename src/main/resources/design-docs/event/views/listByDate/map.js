function(doc) {
	if(doc.$table == "fr.utbm.to52.smarthome.controller.events.StorableEvent"){
		date = new Date(doc.date);
		emit([doc.date,date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear()], doc);
	}
}