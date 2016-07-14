# MpsytServer
Runs mpsyt in a local terminal and allows communication via a browser on remote machines. 

braucht für die installation mps-youtube
dieses wieder braucht youtube-dl

weiters muss, wenn man mps-youtube, oder auch kurz mpsyt aufm raspberry pi verwenden will, das config so verstellen, dass mpsyt den omxplayer verwendet. das erlaubt es standardmäßig nicht, da dies eigentlich offiziell nicht unterstützt wird, man kann es aber reinhacken und es funktioniert trotzdem:

dazu muss man mit der python-Konsole das config file öffnen (findet man in /.config/mps-youtube/config oder aber /BENUTZER/home/.config/mps-youtube/config) und folgende Befehle eingeben:

sudo python
>import pickle
>file = open("config")
>data = pickle.load(file)
>data["PLAYER"]=u'omxplayer'
>file.close()
>file = open("config", "wb")
>pickle.dump(data, file)
>file.close()
>quit()

Bei mir hat er mal die Einstellungen in /.config und mal die einstellungen in /root/home/.config verwendet, also am Besten überall ändern.


----
es findet sich auch in diesem ordner ein script ("mymusicserver") für /etc/init.d
Dieses einfach dorthin verfrachten, die pfade drinnen anpassen und per "sudo update-rc.d mymusicserver defaults" einstellen, dass es mit jedem reboot gestartet wird.

Außerdem kann es dann einfach mit "sudo service mymusicserver start" gestartet bzw. mit "sudo service mymusicserver stop" gestoppt werden.
