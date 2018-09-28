import sqlite3
from core import Core
from flask import Flask, request

application = Flask(__name__)
core = Core()
db = sqlite3.connect('db.db')
cursor = db.cursor()

@application.route('/api/getAddresses')
def getAddresses():
    return core.getNaviaddresses()

@application.route('/api/updateAddress', methods=['POST', 'PUT'])
def updateAddress():
    latitude = float(request.form.get('latitude'))
    longitude = float(request.form.get('longitude'))
    title = request.form.get('title')
    return '', core.updateAddress(title, latitude, longitude)

@application.route('/api/deleteAllAddresses', methods=['DELETE'])
def deleteAllAddresses():
    core.deleteAllAddresses()
    return '', 204

@application.route('/api/auth', methods=['POST'])
def auth():
    login = request.form.get('login')
    password = request.form.get('password')
    return str(core.auth(login, password))

if __name__ == '__main__':
    try:
        cursor.execute("CREATE TABLE addresses (title text, container int, address int, UNIQUE(title))")
        cursor.execute("CREATE TABLE users (login text, password text, UNIQUE(login))")
    except:
        print('Таблица уже существует')
    application.debug = True
    application.run()
