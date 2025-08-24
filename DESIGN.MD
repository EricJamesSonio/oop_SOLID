Note : 
As much as Possible wanting to design a System that follows SRP, OOP and good designs


[Helper classes]
--> Printer class - Uses for Printings since prints shouldn't be inside classes it violates SRP
--> Calculator class - Sales report uses this for computing

[OrderProcessor]        - Processing Orders and Adding it into the Orde Recrods class 
[OrderRecord]           - Acts as a Storage of Receipt by Cashier 
[SalesReport]           - For Vieweing Sales , Filtering Daily, Monthly , Yearly 
[ReceiptRepository]     - Acts as the Main Storage of all Receipts 
[OrderList]             - Acts as Storage for Customer Order (Like a cart in Stores)


{Flow : Customer - Ordering - Paying}

[Customer] --> Uses OrderList for ordering [OrderList] --> Paying [Cashier] --> Cashier uses [OrderProcessor]
           --> [OrderProcessor] convert OrderList into Order --> Checks if Payment >= Totalpayable 
           --> Compute Change --> Order convert into [Receipt] --> Append receipt to [OrderRecord]
           --> [OrderRecord] Append receipt to [ReceiptRepository] 
