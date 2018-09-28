import json
import requests
from config import EMAIL, PASSWORD
from endpoints import *

class Naviaddress:

    def __init__(self):
        self.token = self._getToken()
        self.headers = {'auth-token': self.token, 'Content-Type': 'application/json'}

    def getNaviaddresses(self):
        r = requests.get(ADDRESSES_ENDPOINT + 'my', headers = self.headers)
        jsonData = r.json()['result']
        addresses = []
        for address in jsonData:
            addressData = {'name': address['postal_address'], 'point': address['point']}
            addresses.append(addressData)
        return json.dumps(addresses)

    def createNaviaddress(self, _title, _latitude, _longitude):
        data = '{"lat": 55.761315757185166, "lng": 37.65203475952149, "address_type": "free", "default_lang": "en"}'
        r = requests.post(ADDRESSES_ENDPOINT, data = data, headers = self.headers)
        jsonData = r.json()['result']
        container = jsonData['container']
        address = jsonData['naviaddress']
        self._acceptNaviaddres(container, address)
        self.updateNaviaddress(container, address, _title, _latitude, _longitude)
        return container, address

    def updateNaviaddress(self, _container, _address, _title, _latitude, _longitude):
        data = json.dumps({'name': _title, 'postal_address': _title, 'point': {'lat': _latitude, 'lng': _longitude}, 'lang': 'en'})
        r = requests.put(ADDRESSES_ENDPOINT + str(_container) + '/' + str(_address), data = data, headers = self.headers)

    def deleteAllNaviaddresses(self):
        r = requests.get(ADDRESSES_ENDPOINT + 'my', headers = self.headers)
        jsonData = r.json()['result']
        addresses = []
        for address in jsonData:
            r = requests.delete(ADDRESSES_ENDPOINT + address['container'] + '/' + address['naviaddress'], headers = self.headers)

    def _getToken(self):
        data = {'type': 'email', 'email': EMAIL, 'password': PASSWORD}
        r = requests.post(SESSIONS_ENDPOINT, data = data)
        token = r.json()['token']
        print(token)
        return token

    def _acceptNaviaddres(self, _container, _address):
        r = requests.post(ADDRESSES_ENDPOINT + 'accept/' + str(_container) + '/' + str(_address), headers = self.headers)
