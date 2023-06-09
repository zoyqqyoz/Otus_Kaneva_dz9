#!/bin/bash
if [ -f lock ]
then
echo "previous copy is running"
exit 1
fi
touch lock
 echo "access.log summary": > log.log
 echo "------------------------------------------------------" >> log.log
 Start=$(awk 'NR==1{print $4}' access.log)
 End=$(awk '{print $4}' access.log | awk 'END{print}')
 echo "Start time  = $Start, End time = $End" >> log.log
 echo "------------------------------------------------------" >> log.log
 awk '{print $1}' access.log > ip.log
 echo "10 most popular ip addressess:" >> log.log
 echo "------------------------------------------------------" >> log.log
 sort -n ip.log | uniq -c | sort -rn | head -n 10  >> log.log
 echo "------------------------------------------------------" >> log.log
 awk '{print $7}' access.log > url.log
 echo "10 most popular urls:" >> log.log
 sort -n url.log | uniq -c | sort -rn | head -n 10 >> log.log
 echo "------------------------------------------------------" >> log.log
 echo "error codes:" >> log.log
 awk '{print $9}' access.log | sort | grep ^4 | uniq -c | sort -n >> log.log
 awk '{print $9}' access.log | sort | grep ^5 | uniq -c | sort -n >> log.log
 echo "------------------------------------------------------" >> log.log
 echo "http codes:" >> log.log
 awk '{print $9}' access.log | sort | grep ^1 | uniq -c | sort -n >> log.log
 awk '{print $9}' access.log | sort | grep ^2 | uniq -c | sort -n >> log.log
 awk '{print $9}' access.log | sort | grep ^3 | uniq -c | sort -n >> log.log
 echo "------------------------------------------------------" >> log.log
 mailx -s "Report" root < log.log
 rm log
