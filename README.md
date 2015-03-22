# SoundBash
SoundBash.  It's Sounds Art. It take an audio "pool" (or sound palette to choose from) and uses short samples to recreate the "target" audio file.

Beware:  I'm not a java guy.  This is a fun little project I put together to test an idea.  The idea largely worked, but I don't know where to go with it from here.

A little more detail:
Both pool and target audio are sliced up into tiny little pieces (time span is controlable).  Using frequency analysis, we can find closest matches between slices from each file.  Now, we can recreate one file, using only the tiny slices from the other file entirely.  It sounds weird, but when you play with it, it can be interesting, maybe even cool.

For best artistic effect, I've found that it's best to run this process several times, using different slice time spans.  Then using a digital audio workstation, you can layer the output and see what comes out.

A note about weighting curves.  The curve files provide frequency "bin" definitions.  There is a flat curve provided, which gives equal weight to all frequencies evenly (remember, audio frequencies are logarithmic).  There are also curves which will provide more weight and importance to frequencies used in human speech, since our ears and nueral analysis of sound all caters to hearing other humans.  You can play with which curves are used, but I have foudn best results so far using the B curve, as it is the more heavily weighted curve.


Input for now must be 44.1kH 16bit wav files.
