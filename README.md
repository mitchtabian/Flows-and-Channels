# Flows-and-Channels
Playground for Kotlin Flows and Channels

--------------------------

I made this example as a playground to get familiar with Kotlin Channels and Flows.

Additionally, I was hoping to solve an issue with the way I make requests using MVI architecture. I use a switchmap to detect new StateEvents which then execute the corresponding request in repository layer.

**example**
```java
val dataState: LiveData<DataState<ViewState>> = Transformations
    .switchMap(_stateEvent){ stateEvent ->
        stateEvent?.let {
            handleStateEvent(it) // emit from repo
    }
}
```
The problem with doing it this way is only one request can be executing at any given time. So any concurrency is pretty much out of the question without some serious refactoring. Everything else about this works great so I didn't want to trash it completely.

Turns out Flows are a great way to solve this issue.
