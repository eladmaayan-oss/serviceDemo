package elad.maayan.servicedemo;

import android.media.AudioAttributes;
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

        // דוגמה ל-URL של קובץ MP3 (אתה יכול לשים כאן קובץ משלך)
        String audioUrl2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
        String audioUrl = "https://open.spotify.com/track/7snQQk1zcKl8gZ92AnueZW?si=361aac088b0442ec";

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync(); // טוען ברקע
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                Toast.makeText(getContext(), "מנגן מוזיקה...", Toast.LENGTH_SHORT).show();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(getContext(), "שגיאה בניגון", Toast.LENGTH_SHORT).show();
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "שגיאה בטעינת המוזיקה", Toast.LENGTH_SHORT).show();
        }

        // כפתור להפסקת ניגון (אם תרצה להוסיף)
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
            Toast.makeText(getContext(), "הניגון נעצר", Toast.LENGTH_SHORT).show();
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
