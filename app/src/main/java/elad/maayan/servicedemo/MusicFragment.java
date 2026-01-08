package elad.maayan.servicedemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MusicFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Play music from a local raw resource file
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.sweet_child_o_mine);

        if (mediaPlayer != null) {
            mediaPlayer.start();
            Toast.makeText(getContext(), "Playing music from local file...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error loading music file", Toast.LENGTH_SHORT).show();
        }

        // Button to stop playback
        Button stopButton = view.findViewById(R.id.btnStop);
        if (stopButton != null) {
            stopButton.setOnClickListener(v -> stopMusic());
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(getContext(), "Playback stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}