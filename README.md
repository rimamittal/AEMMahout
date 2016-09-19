# AEM Mahout integration

This is a project is a demo project to showcase the integration of Adobe experience manager(AEM) with Apache Mahout.
Apache Mahout is an algorithm library for scalable machine learning. Machine learning is getting in trend and so we 
thought about Integrating Apache Mahout with AEM to simply make our AEM applications smarter and interesting! 
In this project we have created a User based recommnedation engine which reads user preferences/reviews stored in AEM under
JCR. And based on the data set it provides recommendations to its users.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi AEM-Mahout recommender services,
        AEM-Mahout GetRecommendations Servlet and other utility classes.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode. 
This module includes the Mahout-dependency bundle which is used by core bundle to generate recommendations 
* ui.content: contains sample content using the components from the ui.apps
* ui.tests: Java bundle containing JUnit tests that are executed server-side. This bundle is not to be deployed onto production.
* ui.launcher: contains glue code that deploys the ui.tests bundle to the server

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

Or to deploy only the content, run

    mvn clean install -PautoInstallContent


# Authors

### **_Rima Mittal_**

Contact Info :

* [LinkedIn](https://in.linkedin.com/pub/rima-mittal/13/92/501 "Rima Mittal")
* [Twitter](https://twitter.com/rimamittal)
* [Slideshare](http://www.slideshare.net/puneeshm/)
* [Blog](http://rimamittal.blogspot.in/)


[### **_Ankit gubrani_**](https://github.com/ankit-gubrani/)

Contact Info :

* [LinkedIn](https://in.linkedin.com/pub/ankit-gubrani/74/a75/56b "Ankit Gubrani")
* [Twitter](https://twitter.com/ankitgubrani90)
* [Blog](http://codebrains.blogspot.in/)
* [Slideshare](http://www.slideshare.net/ankitgubrani/)
* [Website](http://www.codebrains.co.in/)