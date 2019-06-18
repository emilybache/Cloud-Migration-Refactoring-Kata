Cloud Migration Refactoring Kata
================================

Inspired by some code I saw recently in the wild.

The "ContentService" is responsible for storing and retrieving content assets from a cloud service. These assets may be images or textual articles. You are in the process of migrating from Google to AWS. You have just modified some code in "getFileBytes" (and related methods), adding AWS as the default and relegating Google to the fallback storage option. You have tested the code manually and everything is working.

Your boss has mandated 80% unit test coverage and since your change, the coverage has fallen below this. Review the existing tests. Add tests for the changed code in "getFileBytes". Refactor the design to be more testable as needed.

Instructions
------------

You may change anything (all of existing code and tests) except:
	
	- The signature of the public methods in "ContentService"
	- The "Content" class

The aim is to get at least 80% test coverage of the ContentService class, including all the code related to retrieving and storing content on AWS and Google. You also want to refactor to make the code and tests easier to maintain in future.

Note: some methods throw an Exception instead of having an implementation. You should not call these methods from your unit tests, they are tested elsewhere (outside of the scope of this exercise).


Questions you can learn about by doing this Kata
-------------------------------------------------

- What is a Partial Mock and when should you use one?
- How should you test private methods?
- How should you test Singleton classes?
- Does mandating 80% test coverage mean you will have good tests that will catch programming errors?
