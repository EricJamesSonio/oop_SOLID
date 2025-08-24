Classes

[Menu Items] Abstract class
-- Name
-- Id
-- Price
-- Recipe 
-- ExpDate

-- GetName
-- GetId
-- GetPrice
-- GetListIngredients
-- GetExpDate
-- GetDetails (Abstract) 

@Override
-- ToString


-- Main Dishes (Base subclass) 
-- Chicken ( subclass) 
--> Fried Chicken leg 
--> Fried Chicken Whole
--> Fried Chicken Breast

-- Side dishes (Base subclass) 
-- Spaghetti
-- To follow

-- Dessert ( Base subclass) 
--> Ice cream
--> Fruit Salad

-- Drinks ( Base subclass) 
--> Water (No price) 
--> Coke (25) 
--> Royal (25) 
--> Sprite (25) 


[ingredients]
-- Name
-- Id
-- ExpDate
-- Price
-- UnitStock

-- GetName
-- GetId
-- GetExpDate
-- GetPrice
-- GetUnitStock

-- Poultry (Base subclass) 
-- Chicken Whole
-- Chicken Leg
-- Chicken breast

-- Dairy ( Base subclass) 
-- Sweets
-- Dairy
-- Cream

[stock]
-- Soft drinks ( Base subclass) 
-- Coke
-- Sprite
-- Royal

-------------------------------------------------------------------------

Customer 
-- Id (Can be null) 
-- OrderList orders;


-- AddtoOrderList
-- RemoveOrder
-- ChangeQuantityOrder
-- Display Orders

-- GetID
-- GetOrderList
-- GetOrderListHashMap

-------------------------------------------------------------------------

IngredientAmount
-- ingredient
-- amount

-- getIngredient
-- getAmount

-------------------------------------------------------------------------

Recipe
-- List <Ingredient Amount>

-- getList

-------------------------------------------------------------------------

OrderList
-- HashMap orders
-- AddtoOrder
-- RemoveOrder
-- ChangeQuantity
-- FindOrder
-- DisplayOrders

-- GetOrders

-------------------------------------------------------------------------

Order
-- Customer
-- SubTotal
-- Discount 
-- TotalPayable

-- ComputeSubTotal
-- ComputeDiscount
-- ComputeTotalPayable
-- Display Orders

-- GetCustomer
-- GetSubtotal
-- GetDiscount
-- GetTotalPayable
-- GetCustomerOrderList (Optional) 


-------------------------------------------------------------------------

OrderProcessor
-- Customer 
-- Payment

[Process Order]
--Customer orderlist Convert into Order 
-- Check if payment sufficient
-- Process Payment - Totalpayable
-- Change
-- Make a Receipt (Order, Payment, Change) 
-- Append to OrderRecords of Cashier

-------------------------------------------------------------------------

Employee - Base class

Cashier (Subclass) 
-- Id
-- Name
-- OrderRecords
-- Order Processor 

-- ProcessOrder (Customer, Payment) 
-- Display Orders

-- GetId
-- GetName
-- GetOrderRecords



Admin 
-- Id
-- Name

-- GetName
-- GetId
-------------------------------------------------------------------------

OrderRecords
-- List(Receipt) 

-- AddToRecords
-- RemoveRecords
-- Modify Records 
-- DisplayRecords

-- GetRecords

-------------------------------------------------------------------------

Discount Strategy(Base class) 
-- Discount Percent
-- Name

-- ApplyDiscount

-- GetName
-- GetDiscountPercent

PWD Discount (Subclass) 


Senior Discount (Subclass)

-------------------------------------------------------------------------

Discount Management Class
-- List (Discount) 

-- AddDiscount
-- RemoveDiscount
-- ModifyDiscount (Change name,Discount) 
-- Display Discounts

-- GetDiscounts
 
-------------------------------------------------------------------------


[Inventory] -- Base Class

Ingredients Inventory (Subclass) 
-- Viewer
-- HashMap (Ingredients) 

-- AddIngredient
-- AddStockqtty
-- RemoveIngredient
-- ModifyIngredient

-- GetStocks

StockInventory (SubClass) 
-- Viewer
-- HashMap (MenuItems) 

-- AddStock
-- AddStockqtty
-- RemoveStock
-- ModifyStock

-- GetStocks

[Inventory Viewer]
-- DisplayInventory

-------------------------------------------------------------------------

Admin 
-- Id
-- Name

-- GetName
-- GetId

------------------------------------------------------------------------

Account
-- Employee
-- Id
-- Username
-- Password

-- GetEmployee
-- GetId
-- GetUsername
-- GetPassword

-------------------------------------------------------------------------

Sales Report
-- List (Receipt) 

-- View Sales Report
-- GetList

-------------------------------------------------------------------------

Sales Report Filterer
-- Daily Sales (If not Admin, Filter by Cashier id) 
-- MonthlySales
-- Yearly sales

-------------------------------------------------------------------------

Receipt 
-- ID
-- Order
-- Payment
-- Change

-- GetId
-- GetOrder
-- GetPayment
-- GetChange
-- DisplayReceipt

-------------------------------------------------------------------------

Restaurant
-- Id
-- Name
-- Location
-- Description

-- GetId
-- GetName
-- GetLocation
-- GetDescriptioClasses