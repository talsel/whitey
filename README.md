# whitey
Incomplete sleep sounds android app I created in 2021 using deprecated SoundPool class.

SoundPool class works better than non-deprecated MediaPlayer class because of the loop handling. MediaPlayer pauses the current sound before playing it from start again, whereas SoundPool simply loops without any noticeable pauses.

### Acknowledged bugs

- Issue with rapid sound changes - i.e. when the user spams buttons, the sounds overlap
- Issue with fadeIn and fadeOut methods - i.e. the stop button doesn't work until the current sound is at full volume
