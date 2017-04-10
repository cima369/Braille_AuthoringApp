README Document for the EECS 2311 Player project:

To install the program, please move the enamel jar file into the Enamel directory folder, and then run the enamel jar file there.
Installation of program only tested on Windows platform. Unable to test installation on other platforms.

Issues that occur which could not be troubleshooted or fixed before submitting:

1) Avoid using the "add set-voice" button or having the line /~set-voice: in the scenario files.
The SoundPlayer class version in this git repositiory may have been accidently changed, where
the mbrola additional voice features are no longer available, and an exception is thrown while
trying to play a scenario file with /~set-voice.

Possible solution may be to change the SoundPlayer class with the working one from the original
git repository at: https://github.com/biltzerpos/enamel


2) Although there is a play menu item, and a play now menu option, all it does is saves the current
scenario file you are working on with the authoring app, to a temporary scenario file called "temp.txt"
which can be found under the folder SampleScenarios. The idea was that after saving the temporary file,
you can then run the SoundPlayer class and play the temporary file, (as seen in the commented out lines
205-207 of the RevisedApp class). However, after attempting to create a new instance of SoundPlayer, 
the simulator window did not show any cells or buttons and ended up being unresponsive for some unknown reason.
Due to this random and unexpected occurence, lines 205-207 of RevisedApp ended up being commented out.

Possible solution: No recommendation for possible solutions. Quite an unexpected interaction if lines
205-207 of RevisedApp were uncommented, and the play now menu item was pressed after uploading or creating
a valid scenario file.

 