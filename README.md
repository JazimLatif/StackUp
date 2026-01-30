# StackOverflow users ranking app

This was an app I wrote to practice some skills I was lacking in, namely navigation, flows and using 2 viewmodels to pass data around an app via a stateful repository.

It started out using one viewmodel, and I added a second screen to get a user, but then the followed and unfollowed states didn't match up, as both VMs held a separate instance of a List<User> and a single User

In order to fix this issue, I made the repository expose a single source of truth of Flow<List<User>>, and the viewmodels observe this, therefore all updates to a user
on one screen, were shown on the other.

I took inspiration from the Now in Android github repo and used Sealed classes to handle state properly.
