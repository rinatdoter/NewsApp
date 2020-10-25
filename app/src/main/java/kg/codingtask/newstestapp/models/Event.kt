package kg.codingtask.newstestapp.models

sealed class Event {
    object  HideLoader: Event()
    class Error(val message: String): Event()
}