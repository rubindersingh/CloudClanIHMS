import urllib2
import json
import threading

global previous,thresholdmax,thresholdmin,add
response1 = urllib2.urlopen('http://localhost:8080/status')
data1 = json.load(response1)
previous = data1["requests"]["total"]
thresholdmax = 20
thresholdmin = 12
add = 0

def getJson():
	global previous
	global thresholdmax
	global thresholdmin
	global add
	threading.Timer(1.0,getJson).start()
	response2 = urllib2.urlopen('http://localhost:8080/status')
	data2 = json.load(response2)   
	current = data2["requests"]["total"]
	requests = current - previous
	print requests
	previous = current
	if add == 0:
		if requests > thresholdmax:
			addserver = urllib2.urlopen('http://localhost:8080/upstream_conf?add=&upstream=backend&server="192.168.1.4:8888  max_conns=100"')
			add = 1
	if add == 1:
		if requests < thresholdmin:
			response3 = urllib2.urlopen('http://localhost:8080/status')
			data3 = json.load(response3)
			id = data3["upstreams"]["backend"]["peers"][1]["id"]
			remserver = ('http://localhost:8080/upstream_conf?remove=&upstream=backend&id=') + str(id)
      			remserverfinal = urllib2.urlopen(remserver)
			add = 0

getJson()
