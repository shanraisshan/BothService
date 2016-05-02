# BothService

**This project contains the indefinitely-running background *LocalService(BothService)*  and multiple client(*Activities A and B*).**

**The *Activities A and B* can directly access the *Service* methods by binding themselves.**

**When the *Activities* un-bind themselves or even the app is closed, the *Service* will keep on running indefinitely**

***______________________________________________________***

There are many issues regarding communication of **multiple activities** with **indefinitely-running service**. Some of the issues from official Android Documentation are mentioned below:

Bound Service (http://developer.android.com/guide/components/bound-services.html#Additional_Notes)

***Note: if multiple activities in your application bind to the same service and there is a transition between two of those activities, the service may be destroyed and recreated as the current activity unbinds (during pause) before the next one binds (during resume). (This activity transition for how activities coordinate their lifecycles is described in the Activities document.)***

Bound Service (http://developer.android.com/guide/components/services.html)

***-Multiple components can bind to the service at once, but when all of them unbind, the service is destroyed.***

***-Multiple clients can bind to the same service and when all of them unbind, the system destroys the service. (The service does not need to stop itself.)***

Android define 2 types of services (**Started** and **Bound**) as mentioned in Official Android Documents (http://developer.android.com/guide/components/services.html)

***Although this documentation generally discusses these two types of services separately, your service can work both ways—it can be started (to run indefinitely) and also allow binding. It's simply a matter of whether you implement a couple callback methods: onStartCommand() to allow components to start it and onBind() to allow binding.***

Let this third type of service be named as **BothService**


The project takes help from the Official Android Documents - Local Service Sample (http://developer.android.com/reference/android/app/Service.html#LocalServiceSample)

![alt tag](https://github.com/shanrais/BothService/blob/master/Shayan/BothService.gif)
