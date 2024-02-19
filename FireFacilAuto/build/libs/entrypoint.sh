#!/bin/bash
echo "nameserver 8.8.8.8" >> /etc/resolv.conf
exec java -jar /opt/app/firefacilauto.jar