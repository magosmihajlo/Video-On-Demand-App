package com.example.presentation.ui.screen.player.components

import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.mediacodec.MediaCodecUtil

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView(
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    val renderersFactory = DefaultRenderersFactory(context)
        .setEnableDecoderFallback(true)
        .setMediaCodecSelector { mimeType, requiresSecureDecoder, requiresTunnelingDecoder ->
            MediaCodecUtil.getDecoderInfos(
                mimeType,
                requiresSecureDecoder,
                requiresTunnelingDecoder
            ).filterNot {
                it.name.contains("amlogic", ignoreCase = true)
            }
        }

    val player = remember {
        ExoPlayer.Builder(context, renderersFactory).build().apply {
            setMediaItem(MediaItem.fromUri(url.toUri()))
            prepare()
            playWhenReady = true
        }
    }

    val playerView = remember {
        PlayerView(context).apply {
            this.player = player
            useController = true
            controllerShowTimeoutMs = 3000

            isFocusable = true
            isFocusableInTouchMode = true
            descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            requestFocus()

            setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode in listOf(
                        KeyEvent.KEYCODE_DPAD_CENTER,
                        KeyEvent.KEYCODE_DPAD_DOWN,
                        KeyEvent.KEYCODE_DPAD_UP,
                        KeyEvent.KEYCODE_DPAD_LEFT,
                        KeyEvent.KEYCODE_DPAD_RIGHT
                    )
                ) {
                    Log.d("ExoPlayerKey", "Key pressed: $keyCode -> Showing controller")
                    showController()
                    true
                } else false
            }

            setControllerVisibilityListener(
                PlayerView.ControllerVisibilityListener { visibility ->
                    if (visibility == View.GONE) {
                        Log.d("ExoPlayerUI", "Controller hidden. Waiting for key input.")
                        this@apply.requestFocus()
                    } else {
                        Log.d("ExoPlayerUI", "Controller shown.")
                    }
                }
            )
        }
    }

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("ExoPlayerLifecycle", "ON_PAUSE")
                    player.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("ExoPlayerLifecycle", "ON_RESUME")
                    player.play()
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("ExoPlayerLifecycle", "ON_STOP")
                    player.release()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    Log.d("ExoPlayerLifecycle", "ON_DESTROY")
                    player.release()
                }
                else -> Log.d("ExoPlayerLifecycle", "Other event: $event")
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            Log.d("ExoPlayerLifecycle", "Releasing player...")
            lifecycleOwner.lifecycle.removeObserver(observer)
            player.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { playerView }
    )
}
