################################################################################
#__author__ = 'Parsa Bagheri'
# CIS 443/543
# Project1
# From http://www.ferg.org/thinking_in_tkinter/tt070_py.txt
# Adapted by A. Hornof 2017
################################################################################

# Package imports
import readchar
import time         # for time.sleep()

# Local imports
import sound        # sound.py accompanies this file

################################################################################
# main()
################################################################################
def main():
    create_sound_filenames()
    verify_sound_filenames()
    create_menu_globals()
    run_menu()

################################################################################
# Create the sound objects for the auditory menus and display.
################################################################################
def create_sound_filenames():

    # Declare global variables.
    global INTRO_WAV, YOU_SELECTED_WAV, NUMBERS_WAV, AM_WAV, PRESS_AGAIN_TO_QUIT_WAV,\
        EXITING_PROGRAM_WAV, EXITING_PROGRAM_WAV_DURATION, TMP_FILE_WAV, PM_WAV, SELECT_DAY_OF_THE_WEEK, DAYS_OF_WEEK_WAV\
        , SET_HOUR, SET_MIN

    # Create  sounds.
    nav_path = "wav_files_provided/miscellaneous_f/"
    num_path = "wav_files_provided/numbers_f/"
    days_of_week_path = "wav_files_provided/days_of_week_f/"
    INTRO_WAV = nav_path + "Intro_f.wav"
    YOU_SELECTED_WAV = nav_path + "you_selected_f.wav"
    SELECT_DAY_OF_THE_WEEK = nav_path + "Set_day_of_week_f.wav"
    SET_HOUR = nav_path + "Set_hour_f.wav"
    SET_MIN = nav_path + "Set_minutes_f.wav"
    NUMBERS_WAV = [num_path + "00_f.wav",num_path + "01_f.wav",num_path + "02_f.wav",num_path + "03_f.wav",num_path + "04_f.wav",num_path + "05_f.wav",
                   num_path + "06_f.wav",num_path + "07_f.wav",num_path + "08_f.wav",num_path + "09_f.wav",num_path + "10_f.wav",num_path + "11_f.wav",
                   num_path + "12_f.wav",num_path + "13_f.wav",num_path + "14_f.wav",num_path + "15_f.wav",num_path + "16_f.wav",num_path + "17_f.wav",
                   num_path + "18_f.wav",num_path + "19_f.wav",num_path + "20_f.wav",num_path + "21_f.wav",num_path + "22_f.wav",num_path + "23_f.wav",
                   num_path + "24_f.wav",num_path + "25_f.wav",num_path + "26_f.wav",num_path + "27_f.wav",num_path + "28_f.wav",num_path + "29_f.wav",
                   num_path + "30_f.wav",num_path + "31_f.wav",num_path + "32_f.wav",num_path + "33_f.wav",num_path + "34_f.wav",num_path + "35_f.wav",
                   num_path + "36_f.wav",num_path + "37_f.wav",num_path + "38_f.wav",num_path + "39_f.wav",num_path + "40_f.wav",num_path + "41_f.wav",
                   num_path + "42_f.wav",num_path + "43_f.wav",num_path + "44_f.wav",num_path + "45_f.wav",num_path + "46_f.wav",num_path + "47_f.wav",
                   num_path + "48_f.wav",num_path + "49_f.wav",num_path + "50_f.wav",num_path + "51_f.wav",num_path + "52_f.wav",num_path + "53_f.wav",
                   num_path + "54_f.wav",num_path + "55_f.wav",num_path + "56_f.wav",num_path + "57_f.wav",num_path + "58_f.wav",num_path + "59_f.wav",]
    DAYS_OF_WEEK_WAV = [days_of_week_path+ "monday_f.wav", days_of_week_path+ "tuesday_f.wav", days_of_week_path+ "wednesday_f.wav",
                        days_of_week_path + "thursday_f.wav", days_of_week_path+ "friday_f.wav", days_of_week_path+ "saturday_f.wav",
                        days_of_week_path + "sunday_f.wav"]
    AM_WAV = nav_path + "AM_f.wav"
    PM_WAV = nav_path + "PM_f.wav"
    PRESS_AGAIN_TO_QUIT_WAV = nav_path + "Press_again_to_quit_f.wav"
    EXITING_PROGRAM_WAV = nav_path + "Exiting_program_f.wav"
    EXITING_PROGRAM_WAV_DURATION = 1.09 # in s. 1.09 is accurate but 0.45 saves time.
    TMP_FILE_WAV = "tmp_file_p782s8u.wav" # Random filename  for output

################################################################################
# Verify all files can be loaded and played.
# Play all sound files to make sure the paths and filenames are correct and valid.
# The very last sound tested/played should be the sound that plays at startup.
################################################################################
def verify_sound_filenames():
    sound.Play(INTRO_WAV)

################################################################################
# Create some global constants and variables for the menu.
################################################################################
def create_menu_globals():

    # Declare global variables as such.
    global FORWARD_KEY, BACKWARD_KEY, QUIT_KEY, MINIMAL_HELP_STRING, CURRENT_TIME, SET_KEY, START_KEY,BACKWARD_AND_FORWARD_INSTRUCTIONS_DAY,BACKWARD_AND_FORWARD_INSTRUCTIONS_HOUR, BACKWARD_AND_FORWARD_INSTRUCTIONS_MINS

    # Constants
    # Keystrokes for the keyboard interaction.
    FORWARD_KEY = '\x20' # space bar
    BACKWARD_KEY = ';'
    QUIT_KEY = 'j'
    SET_KEY = 'k'
    START_KEY = 'l'

   
    # A bare minimum of text to display to guide the user.
    MINIMAL_HELP_STRING = "Press '" + QUIT_KEY + "' to quit.\nPress '" + START_KEY + "' to select time.\n"
    BACKWARD_AND_FORWARD_INSTRUCTIONS_DAY = "Press '"+"Space Bar"+"' to go to the next day.\nPress '"+BACKWARD_KEY+"'to go to the previous day.\nPress'"+SET_KEY+"'to set the day.\n"
    BACKWARD_AND_FORWARD_INSTRUCTIONS_HOUR = "Press '"+"Space Bar"+"' to go forward.\nPress '"+BACKWARD_KEY+"'to go backward.\nPress'"+SET_KEY+"'to set the hour.\n"
    BACKWARD_AND_FORWARD_INSTRUCTIONS_MINS = "Press '"+"Space Bar"+"' to go forward.\nPress '"+BACKWARD_KEY+"'to go backward.\nPress'"+SET_KEY+"'to set the minutes.\n"

    # Global variables
    CURRENT_TIME = 0    # The current time that is set. (Just an integer for now.)


################################################################################
# Run the menu in an endless loop until the user exits.
################################################################################
def run_menu():

    global CURRENT_MINUTE, CURRENT_HOUR, CURRENT_DAY

    # Provide a minimal indication that the program has started.
    print(MINIMAL_HELP_STRING)

    # Get the first keystroke.
    c = readchar.readchar()

    # Endless loop responding to the user's last keystroke.
    # The loop breaks when the user hits the QUIT_MENU_KEY.
    while True:

        # Respond to the user's input.
        if c == START_KEY:
            CURRENT_DAY = 0
            CURRENT_HOUR = 1
            CURRENT_MINUTE = 0


            #Print the set of instructions for setting the day
            print(BACKWARD_AND_FORWARD_INSTRUCTIONS_DAY)
            sound.Play(SELECT_DAY_OF_THE_WEEK)
            time.sleep(EXITING_PROGRAM_WAV_DURATION)
            sound.Play(DAYS_OF_WEEK_WAV[CURRENT_DAY])
            c = readchar.readchar()



            # Following loop allows the user to choose the day, it breaks when the user presses the SET_KEY
            while True:

                if c == FORWARD_KEY: #increments or resets the current_day value 
                    if CURRENT_DAY != 6:
                        CURRENT_DAY+=1
                    else:
                        CURRENT_DAY=0
                    sound.Play(DAYS_OF_WEEK_WAV[CURRENT_DAY])
                    c = readchar.readchar()
        
                elif c == BACKWARD_KEY: #decrements or resets the current_day value 
                    
                    if CURRENT_DAY != 0:
                        CURRENT_DAY-=1
                    else:
                        CURRENT_DAY=6
                    sound.Play(DAYS_OF_WEEK_WAV[CURRENT_DAY]) #plays the sound file corresponding to the index of array containing the day of the week sound files
                    c = readchar.readchar()
                elif c == SET_KEY:
                    break
                else:
                    # Get the user's next keystroke.
                    c = readchar.readchar()
            #end while



            #Print the set of instructions for setting the hour
            print(BACKWARD_AND_FORWARD_INSTRUCTIONS_HOUR)
            sound.Play(SET_HOUR)
            time.sleep(EXITING_PROGRAM_WAV_DURATION)
            sound.Play(NUMBERS_WAV[CURRENT_HOUR])
            c = readchar.readchar()



            #The following loop is for setting hour, the hours ranges from 0-23, it exits the loop by pressing 'set' or 'quit.'
            while True:

                if c == FORWARD_KEY:

                    if CURRENT_HOUR != 23:
                        CURRENT_HOUR += 1
                    else:
                        CURRENT_HOUR = 0
                    
                    sound.Play(NUMBERS_WAV[CURRENT_HOUR])
                    c = readchar.readchar()
                elif c == BACKWARD_KEY:

                    if CURRENT_HOUR != 0:
                        CURRENT_HOUR -= 1
                    else:
                        CURRENT_HOUR = 23

                    sound.Play(NUMBERS_WAV[CURRENT_HOUR])
                    c = readchar.readchar()
                elif c == SET_KEY:
                    break
                else:
                    c = readchar.readchar()
            #end while



            #Print the set of instructions for setting the minutes
            print(BACKWARD_AND_FORWARD_INSTRUCTIONS_MINS)
            sound.Play(SET_MIN)
            time.sleep(EXITING_PROGRAM_WAV_DURATION)
            sound.Play(NUMBERS_WAV[CURRENT_MINUTE])
            c = readchar.readchar()



            #The following loop is for setting minutes, the minutes ranges from 0-59, it exits the loop by pressing 'set' or 'quit.'
            while True:
                if c == FORWARD_KEY:
                    
                    if CURRENT_MINUTE != 59:
                        CURRENT_MINUTE += 1
                    else:
                        CURRENT_MINUTE = 0
                    
                    sound.Play(NUMBERS_WAV[CURRENT_MINUTE])
                    c = readchar.readchar()
                elif c == BACKWARD_KEY:
                
                    if CURRENT_MINUTE != 0:
                        CURRENT_MINUTE -= 1
                    else:
                        CURRENT_MINUTE = 59
            
                    sound.Play(NUMBERS_WAV[CURRENT_MINUTE])
                    c = readchar.readchar()
                elif c == SET_KEY:
                    break
                else:
                    c = readchar.readchar()
  

            #if the time is in the AM
            if CURRENT_HOUR <= 12:
            	sound.combine_wav_files(TMP_FILE_WAV, YOU_SELECTED_WAV, DAYS_OF_WEEK_WAV[CURRENT_DAY], NUMBERS_WAV[CURRENT_HOUR], NUMBERS_WAV[CURRENT_MINUTE], AM_WAV)
            	sound.Play(TMP_FILE_WAV)

            # if the time is in the PM
            if CURRENT_HOUR > 12:
            	sound.combine_wav_files(TMP_FILE_WAV, YOU_SELECTED_WAV, DAYS_OF_WEEK_WAV[CURRENT_DAY], NUMBERS_WAV[CURRENT_HOUR - 12], NUMBERS_WAV[CURRENT_MINUTE], PM_WAV)
            	sound.Play(TMP_FILE_WAV)

            #Making the program sleep until the combined wav files is done being reported
            time.sleep(EXITING_PROGRAM_WAV_DURATION)
            time.sleep(EXITING_PROGRAM_WAV_DURATION)
            time.sleep(EXITING_PROGRAM_WAV_DURATION)
            time.sleep(EXITING_PROGRAM_WAV_DURATION)

            #Intructions for starting over or exiting the program
            sound.Play(INTRO_WAV)
            print(MINIMAL_HELP_STRING)
            c = readchar.readchar()

        # User quits.
        elif c == QUIT_KEY:

            # Notify the user that another QUIT_MENU_KEY will quit the program.
            sound.Play(PRESS_AGAIN_TO_QUIT_WAV)

            # Get the user's next keystroke.
            c = readchar.readchar()

            # If the user pressed QUIT_MENU_KEY, quit the program.
            if c == QUIT_KEY:
                sound.Play(EXITING_PROGRAM_WAV)
                # A delay is needed so the sound gets played before quitting.
                time.sleep(EXITING_PROGRAM_WAV_DURATION)
                sound.cleanup()
                # Quit the program
                break

        # The user presses a key that will have no effect.
        else:
            # Get the user's next keystroke.
            c = readchar.readchar()

################################################################################
main()
################################################################################


