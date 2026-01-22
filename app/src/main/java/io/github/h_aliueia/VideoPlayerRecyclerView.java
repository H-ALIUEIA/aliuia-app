package io.github.h_aliueia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.FileDataSource;
import androidx.media3.datasource.cache.CacheDataSink;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.RequestManager;
import io.github.h_aliueia.models.MediaObject;

import java.util.ArrayList;

import io.github.h_aliueia.interfaces.interfaceclass;
import io.github.h_aliueia.utils.cache;

public class VideoPlayerRecyclerView extends RecyclerView
{
    private static final String TAG = "VideoPlayerRecyclerView";
    private enum VolumeState {ON, OFF};
    private ImageView thumbnail;
    private ProgressBar progressBar;
    private View viewHolderParent;
    private FrameLayout frameLayout;
    private PlayerView videoSurfaceView;
    private ExoPlayer videoPlayer;
    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private Context context;
    private int playPosition = -1;
    private boolean isVideoViewAdded;
    private RequestManager requestManager;
    // controlling playback state
    private VolumeState volumeState;
    private interfaceclass mListener;
    private int targetPosition;
    protected boolean mRequestedLayout = false;
    @SuppressLint("UnsafeOptInUsageError")
    private CacheDataSource.Factory cacheDataSourceFactory;


    @UnstableApi
    public VideoPlayerRecyclerView(@NonNull Context context)
    {
        super(context);
        init(context);
    }

    @UnstableApi
    public VideoPlayerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public void interfacelistener(interfaceclass listener)
    {
        mListener = listener;
    }

    @UnstableApi
    public void first()
    {
        LinearLayoutManager mLayoutManager = (LinearLayoutManager) this.getLayoutManager();
        int pos=mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if(pos == 0)
        {
            playVideo(false);
            mListener.changedata(pos);
        }
    }

    @UnstableApi
    private void init(Context context)
    {
        this.context = context.getApplicationContext();
        cache();
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;
        videoSurfaceView = new PlayerView(this.context);
        // 2. Create the player
        videoPlayer = new ExoPlayer.Builder(context).build();
        videoSurfaceView.setShowPreviousButton(false);
        videoSurfaceView.setShowNextButton(false);
        videoSurfaceView.findViewById(androidx.media3.ui.R.id.exo_settings).setVisibility(GONE);
        // Bind the player to the view.
        videoSurfaceView.setUseController(true);
        videoSurfaceView.setControllerAutoShow(false);
        videoSurfaceView.setPlayer(videoPlayer);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        addOnScrollListener(new OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int pos=mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if(pos == -1)
                    {
                        pos = mLayoutManager.findFirstVisibleItemPosition();
                    }
                    mListener.changedata(pos);
                    if(thumbnail != null)
                    { // show the old thumbnail
                        thumbnail.setVisibility(VISIBLE);
                    }
                    if(!recyclerView.canScrollVertically(1))
                    {
                        playVideo(true);
                    }
                    else
                    {
                        playVideo(false);
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener()
        {
            @Override
            public void onChildViewAttachedToWindow(View view)
            {

            }
            @Override
            public void onChildViewDetachedFromWindow(View view)
            {
                if (viewHolderParent != null && viewHolderParent.equals(view))
                {
                    resetVideoView();
                }

            }
        });
        videoPlayer.addListener(new Player.Listener()
        {
            @Override
            public void onPlaybackStateChanged(int playbackState)
            {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
                switch (playbackState)
                {

                    case Player.STATE_BUFFERING:
                        if (progressBar != null)
                        {
                            progressBar.setVisibility(VISIBLE);
                        }

                        break;
                    case Player.STATE_ENDED:
                        videoPlayer.seekTo(0);
                        break;
                    case Player.STATE_IDLE:

                        break;
                    case Player.STATE_READY:
                        if (progressBar != null)
                        {
                            progressBar.setVisibility(GONE);
                        }
                        if(!isVideoViewAdded)
                        {
                            addVideoView();
                        }
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onRepeatModeChanged(int repeatMode)
            {

            }
            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled)
            {

            }
            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters)
            {

            }
        });
    }

    @UnstableApi
    public void cache()
    {
        try
        {
            CacheDataSink.Factory cacheSink = new CacheDataSink.Factory().setCache(cache.initCache(this.getContext()));
            DefaultDataSource.Factory upstreamFactory = new DefaultDataSource.Factory(this.getContext(), new DefaultHttpDataSource.Factory());
            FileDataSource.Factory downStreamFactory = new FileDataSource.Factory();
            cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(cache.initCache(this.getContext()))
                .setCacheWriteDataSinkFactory(cacheSink)
                .setCacheReadDataSourceFactory(downStreamFactory)
                .setUpstreamDataSourceFactory(upstreamFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        }
        catch (IllegalStateException e)
        {

        }
    }

    @UnstableApi
    public void playVideo(boolean isEndOfList)
    {
        videoSurfaceView.hideController();
        if(!isEndOfList)
        {
            int startPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1)
            {
                endPosition = startPosition + 1;
            }
            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0)
            {
                return;
            }
            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition)
            {
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);
                targetPosition = startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            }
            else
            {
                targetPosition = startPosition;
            }
        }
        else
        {
            targetPosition = mediaObjects.size() - 1;
        }
        // video is already playing so return
        if (targetPosition == playPosition)
        {
            return;
        }
        // set the position of the list-item that is to be played
        playPosition = targetPosition;
        if (videoSurfaceView == null)
        {
            return;
        }
        // remove any old surface views from previously playing videos
        videoSurfaceView.setVisibility(INVISIBLE);
        removeVideoView(videoSurfaceView);
        int currentPosition = targetPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        View child = getChildAt(currentPosition);
        if (child == null)
        {
            return;
        }
        VideoPlayerViewHolder holder = (VideoPlayerViewHolder) child.getTag();
        if (holder == null)
        {
            playPosition = -1;
            return;
        }
        thumbnail = holder.thumbnail;
        progressBar = holder.progressBar;
        viewHolderParent = holder.itemView;
        requestManager = holder.requestManager;
        frameLayout = holder.itemView.findViewById(R.id.media_container);
        videoSurfaceView.setPlayer(videoPlayer);
        String mediaUrl = mediaObjects.get(targetPosition).getMedia_url();
        if (mediaUrl != null)
        {
            MediaItem mediaItem = MediaItem.fromUri(mediaUrl);
            SharedPreferences sharedPref = getContext().getSharedPreferences(getContext().getString(R.string.localstorage), Context.MODE_PRIVATE);
            if(!(offlinegetter.offlinechecker(getContext(),1)))
            {
                MediaSource mediaSource = new HlsMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem);
                videoPlayer.setMediaSource(mediaSource);
            }
            else
            {
                videoPlayer.setMediaItem(mediaItem);
            }
            videoPlayer.prepare();
            videoPlayer.setPlayWhenReady(true);
        }
    }
    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     * @param playPosition
     * @return
     */
    private int getVisibleVideoSurfaceHeight(int playPosition)
    {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        View child = getChildAt(at);
        if (child == null)
        {
            return 0;
        }
        int[] location = new int[2];
        child.getLocationInWindow(location);

        if (location[1] < 0)
        {
            return location[1] + videoSurfaceDefaultHeight;
        }
        else
        {
            return screenDefaultHeight - location[1];
        }
    }

    // Remove the old player
    private void removeVideoView(PlayerView videoView)
    {
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent == null)
        {
            return;
        }

        int index = parent.indexOfChild(videoView);
        if (index >= 0)
        {
            parent.removeViewAt(index);
            isVideoViewAdded = false;
            viewHolderParent.setOnClickListener(null);
        }
    }
    private void addVideoView()
    {
        frameLayout.addView(videoSurfaceView);
        isVideoViewAdded = true;
        videoSurfaceView.requestFocus();
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setAlpha(1);
        thumbnail.setVisibility(GONE);
    }
    private void resetVideoView()
    {
        if(isVideoViewAdded)
        {
            removeVideoView(videoSurfaceView);
            playPosition = -1;
            videoSurfaceView.setVisibility(INVISIBLE);
            thumbnail.setVisibility(VISIBLE);
        }
    }
    public void releasePlayer()
    {
        if (videoPlayer != null)
        {
            videoPlayer.release();
            videoPlayer = null;
        }
        viewHolderParent = null;
    }
    public void setMediaObjects(ArrayList<MediaObject> mediaObjects)
    {
        this.mediaObjects = mediaObjects;
    }
}
