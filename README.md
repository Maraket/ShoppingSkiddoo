# ShoppingSkiddoo

There were some issues during the development of this small challenge presented people should be aware of:

* Using SBT-Eclipse plugin DOES NOT add the java builder to the eclipse project, this will cause untold misery and confusion as to why you are getting the error:
```Error: Could not find or load main class <CLASS XYZ>```
** Simplest solution I found was here https://www.abeel.be/content/howto-change-default-eclipse-project-eclipse-java-project which worked for Eclipse 4.9.0
** This sadly took me longer then it should have
* Having not been exposed to SBT files in the past, the solution if you are an eclipse user is [SBT](https://www.scala-sbt.org/) and (SBTEclipse)[https://github.com/sbt/sbteclipse]


# Important Notes about design Decisions / ToDo's

1. Additional Test Cases to more thoroughly test exceptions for the SpecialRules
2. Due to the Simplicity of the Product store (it's basically a wrapper around a hashmap), I did not complete a JUnit Test Case
3. The PricingRules implementation logic is relatively simple, and did not do a Junit Test Case
4. Keeping the file names that the project started with, the naming convention of <Interface_Name>_Impl is not my usual go to
5. I didn't preset the size of the Array lists I created due to the nature of this test not needing to be highly optimized
6. Similarly it was not expected this code would be expected to run on a threaded environment, as such this code is definitely not thread safe
    * I would fix that by adding synchronized at various calls such as during the addition of the PricingRules and ProductStore as some examples.
7. The assumption that SKU's are considered Unique guided the use of a HashMap for the product store, should SKU prove to not be unique, then likely a different approach would be needed with a clear definition of what defines a unique product.
8. It was not clarified if a product could be subject to multiple Specials, so by default it was assumed they could, but some basic logic was included to force specials to only apply to products that had no other specials, but this is only enabled with a boolean flag in the constructor. 
    * the addition of more comprehensive discount structures would need a more concise clarification of rules surrounding Specials, their applications, exclusions .etc
9. Although Currency was not used, this was more to give Price a clearer identity that this was a sum of money, rather then an abstract concept of price. As such, in theory this could be extended to take into account exchange rates, internationalization .etc
10. The use of BigDecimal was the basic choice, as the pricing was expected to be a money form, though in production systems the use of a more tightly managed and atomic Money system like [Java Money](https://github.com/JavaMoney/jsr354-api) or any other system built to comply with JSR 354 would be more suitable.
