import sqlite3
from naviaddress import Naviaddress

class Core(Naviaddress):

    def updateAddress(self, _title, _latitude, _longitude):
        db = sqlite3.connect('db.db')
        cursor = db.cursor()
        returnCode = 0
        returnMsg = ''
        cursor.execute("SELECT * FROM addresses WHERE title = ?", (_title,))
        rows = cursor.fetchall()
        if rows == []:
            container, address = self.createNaviaddress(_title, _latitude, _longitude)
            cursor.execute("INSERT INTO addresses VALUES (?, ?, ?)", (_title, container, address))
            returnCode = 201
        else:
            data = rows[0]
            self.updateNaviaddress(data[1], data[2], _title, _latitude, _longitude)
            returnCode = 204
        db.commit()
        cursor.close()
        return returnCode
