Java
    -Application is tested using Java 17

MySQL
    -MySQL running instance in localhost is required to execute this application
    -If mysql is running in network, configure ip accordingly in application.properties


For API  GET /fx
    -API is implemented to fetch target currencies as per the property "query.currencies" in property file

For API GET /fx/{targetCurrency}
    -API fetches the history for 'n' days for the specified targetCurrency where 'n' can be configured in property file (history.noofdays)
    -If exchange is not available for current date, API returns the latest available conversions for 'n' days
        For example, if API is executed on 07/07/2024 and 'n' is configured as 3, it returns the conversions for 03/07/2024, 04/07/2024 and 05/07/2024
                     if API is executed on 08/07/2024 and 'n' is configured as 3, it returns the conversions for 04/07/2024, 05/07/2024 and 08/07/2024

Data Migration (Flyway)
    -No need for separate installation. Maven loads the required libraries
    -No need to create schema or table. Flyway takes care of creating the schema/table if it does not exist

Testing
    -Both end points are tested in the ConversionTest.java class
    -Test checks whether the response status is ok and also prints the response
    -No Scenario based testing is covered in this test as the requirement demands only endpoint testing

Conversion Logic
    -External API returns the conversions with EUR as base currency
    -As per requirement, this API is supposed to return conversions with USD as base currency. Hence below formula is used to calculate the exchange rate
        -External API gives EUR to GBP and EUR to USD (assuming GBP is TGTCURR)
        -To calculate USD to GBP, EURtoTGTCURR is divided by EURtoUSD
        -USD to EUR is calculated as 1/EURtoUSD
    -Base currency USD is hard coded and not configurable. But with some changes in the code, base currency can also be made configurable

Exception and Logging
    -Though Exception and Logging are vital part of any application, this application does not cater both features as the focus is more on accuracy of the implementation
