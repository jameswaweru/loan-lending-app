# loan-lending-app
This is a loan lending backend application written in java - springboot framework

### Overview
By Default new customers have a maximum allowable limit of 1000.
This limit can however be updated.

### Assumptions

Interest rate is compounded on Monthly Basis (Per month)

Tenure is given Months(in calculation), but please note for loan offers we are returning tenure in days 


Loan offers are calculated using this protocol
* First we check the loan amount customer has applied for , falls under which product, product A or Product B.
  
  If the amount is within the range of 0 to 10000, this is product A . Amount within the range of 10001 to 25000 is product B. Any other amount is rejected.
  
 * Second , we calculate daily interest rate , tenure in days and daily payment , using the Loan product values selected above.
 
 * Lastly , we calculate loan offers by ;
   
   - Reducing or increasing daily payment (Daily Payment - this is amount customer is supposed to pay on daily basis and its calculated using the Loan Product(A or B) values)
 
   - Reducing or increasing tenure (Tenure is given in days)
   
 ### Getting Started
   
   * Ensure you have installed the below applications
   
       |Application|Version|
       |---------|------------|
       |JDK| \> 11 |
   
   * Application is using h2 database, no installation of db required
   
   ### Setup
   
   1. git clone https://github.com/jameswaweru/loan-lending-app.git
   
   2. mvn clean install package 
      
      Application is running on port: 2020
      
  ### Rest Invocation
  
  Initialize Loan Products
  * URL
  
      http://127.0.0.1:2020/api/loans/saveLoanProducts
  
  * Request Body 
  
      `[
           {
               "loanProductId":1,
               "loanProductName":"Loan Produt A",
               "loanProductMaxLimit":"10000",
               "loanProductMinLimit":"1",
               "tenureRate":"10",
               "tenure":"0.5"
           },
           {
               "loanProductId":2,
               "loanProductName":"Loan Produt B",
               "loanProductMaxLimit":"25000",
               "loanProductMinLimit":"10001",
               "tenureRate":"12.5",
               "tenure":"1"
           }
       ]`
  
   Apply Loan
   * URL
    
        http://127.0.0.1:2020/api/loans/apply
    
   * Request Body 
    
        `{
             "customerMobile":"726765977",
             "loanAmount":"5000"
         }`
         
   Change Customer Limit
    * URL
     
         http://127.0.0.1:2020/api/loans/changeCustomerLimit
     
   * Request Body 
     
       `{
              "msisdn":"726765977",
              "newLoanLimit":"5000"
          }`
          
   Select/Accept Loan Offer
    * URL
     
         http://127.0.0.1:2020/api/loans/acceptLoanOffer
     
   * Request Body 
     
       `{
            "offerCode":"2323",
            "loanAmount":"5000"
        }`

### Application Flaws
 
 * Application hasn't been thoroughly tested on accuracy.
 * Loan offers configs are static, they have to be updated manualy in the application properties & rerun the application
 