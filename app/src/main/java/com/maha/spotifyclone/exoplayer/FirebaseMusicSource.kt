package com.maha.spotifyclone.exoplayer

import com.maha.spotifyclone.exoplayer.State.*

class FirebaseMusicSource {

    private val onReadyListeners = mutableListOf<(Boolean) -> Unit> ()

    private var state: State = STATE_CREATED
        set(value) {
            if(value == STATE_INITIALIZED || value == STATE_ERROR){
                synchronized(onReadyListeners){
                    field = value
                    onReadyListeners.forEach { listener ->
                        listener(state == STATE_INITIALIZED)

                        // https://www.youtube.com/watch?v=-rqYHUUpuqE&list=PLQkwcJG4YTCT-lTlkOmE-PpRkuyNXq6sW&index=5&ab_channel=PhilippLackner
                        // 8:33
                    }
                }
            }else {
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit): Boolean{
        if (state == STATE_CREATED || state == STATE_INITIALIZING) {
            onReadyListeners += action
            return false
        }else {
            action(state == STATE_INITIALIZED)
            return true

        }
    }


}



enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}