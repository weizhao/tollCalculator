# How to run this project?
```
git clone https://github.com/weizhao/tollCalculator.git
cd tollCalculator
mvn package
java -jar target/tollCalculator.jar toll.fee.cal.CostOfTrip "QEW" "Highway 400"
java -jar target/tollCalculator.jar toll.fee.cal.CostOfTrip "QEW" "Salem Road"
java -jar target/tollCalculator.jar toll.fee.cal.CostOfTrip "Salem Road" "QEW"
```

## How to run Unit Test
```
mvn test
```
# Assumption
```
The location ids consistent with the interchage's next and previous setting, that is the next order in the locations is the larger "toId" and (if any) the previous order in the locations is the smaller "toId".

```
