import time
import serial
import smtplib

ser = serial.Serial(port='/dev/tty.usbmodem1411', baudrate=9600)
    
while True:
    message = ser.readline().strip()

    print(message)

    import urllib
    import urllib2

    url = 'http://parking.ngrok.com/updateparking'
    values = {'id' : '1',
              'inc' : '1' }
    
    data = urllib.urlencode(values)
    req = urllib2.Request(url, data)
    response = urllib2.urlopen(req)
    the_page = response.read()
    
    print '\a'
