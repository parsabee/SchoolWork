#__author__ = 'Parsa Bagheri'
# CIS 443/543
# Project3
# From http://www.ferg.org/thinking_in_tkinter/tt070_py.txt
# Adapted by A. Hornof 2017

from tkinter import *
from PIL import Image, ImageTk
import sound
import time
import sys

class TimeScheduler:

	# Function that is called upon clicking the "RIGHT" button
	def forwardKey(self, event):
		global STAGE
		val = forward(self, STAGE)
		self.timeLabel.configure(text=val)
		# print(val)

	# Function that is called upon clicking the "LEFT" button
	def backKey(self, event):
		global STAGE
		val = back(self, STAGE)
		self.timeLabel.configure(text=val)
		# print(val)

	# Function that is called upon clicking the "SET" button
	def setKey(self, event):
		global TIMEUNIT
		time_val = set(self)
		# print(time_val)

	def __init__(self, parent):
		createSoundFilenames()
		#Setting up the frame

		# Entering the main menu upon start-up
		if HOUR < 12:
			sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR], NUMBERS_WAV[MINUTE], AM_WAV)
		else:
			sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR-12], NUMBERS_WAV[MINUTE], PM_WAV)
		sound.Play(TMP_FILE_WAV)

		# setting up the widgets in the parent frame
		self.myParent = parent
		self.myConstrainer1 = Frame(parent)
		self.myConstrainer1.pack(fill="both", expand=True)
		self.myParent.bind("<k>", self.forwardKey)
		self.myParent.bind("<j>", self.backKey)
		self.myParent.bind("<space>", self.setKey)

		self.currentTime = Label(self.myConstrainer1)
		self.currentTime = Label(text="Entering Main Menu. Current day and time are: Monday 00:00 AM")
		self.currentTime.configure(height=3, width= 55)
		self.currentTime.place(x=40, y=40)

		self.timeLabel = Label(self.myConstrainer1)
		self.timeLabel.configure(text ="Set Day")
		self.timeLabel.configure(font=("TkDefaultFont", 12))
		self.timeLabel.configure(height=3, width=10)
		self.timeLabel.place(x=250, y=80)

		self.forwardButton = Button(self.myConstrainer1)
		self.forwardButton.configure(text="RIGHT", background="RoyalBlue3")
		self.forwardButton.configure(font=("TkDefaultFont", 12, "bold"))
		self.forwardButton.configure(height = 2, width = 5)
		self.forwardButton.place(x=300, y=270)
		self.forwardButton.bind("<Button-1>", self.forwardKey)
		self.forwardButton.bind("k", self.forwardKey)

		self.backButton = Button(self.myConstrainer1)
		self.backButton.configure(text="LEFT", background="RoyalBlue3")
		self.backButton.configure(font=("TkDefaultFont", 12, "bold"))
		self.backButton.configure(height = 2, width = 5)
		self.backButton.place(x=220, y=270)
		self.backButton.bind("<Button-1>", self.backKey)
		self.backButton.bind("j", self.backKey)

		self.setButton = Button(self.myConstrainer1)
		self.setButton.configure(text="SET", background="RoyalBlue3")
		self.setButton.configure(font=("TkDefaultFont", 12, "bold"))
		self.setButton.configure(height = 1, width = 15)
		self.setButton.place(x=220, y=330)
		self.setButton.bind("<Button-1>", self.setKey)
		self.setButton.bind("space", self.setKey)

		# shows the main menu upon start-up
		makeMainMenu(self)

def makeMainMenu(self):
	"""Sets up the label widgets for the main menu"""

	global SET_COUNTER

	self.mainMenu = Frame(self.myConstrainer1)
	self.mainMenu.configure(height=20, width=300)
	self.mainMenu.place(x=100, y= 150)

	self.setday = Label(self.mainMenu, text="|     Set Day    |")
	self.setday.configure(height=1, width=10)
	self.setday.grid(row=0, column=1)

	self.sethour = Label(self.mainMenu, text="|   Set Hour    |")
	self.sethour.configure(height=1, width=10)
	self.sethour.grid(row=0, column=2)

	self.setminute = Label(self.mainMenu, text="|  Set Minute   |")
	self.setminute.configure(height=1, width=10)
	self.setminute.grid(row=0, column=3)

	self.exit = Label(self.mainMenu, text="|Exiting Program|")
	self.exit.configure(height=1, width=12)
	self.exit.grid(row=0, column=4)

	if SET_COUNTER == 1:
		self.setday.configure(background="white")
		self.setminute.configure(background="white")
		self.exit.configure(background="white")
		self.sethour.configure(background="grey")
	elif SET_COUNTER == 2:
		self.setday.configure(background="white")
		self.sethour.configure(background="white")
		self.setminute.configure(background="grey")
		self.exit.configure(background="white")
	elif SET_COUNTER == 3:
		self.setday.configure(background="white")
		self.sethour["background"] = "white"
		self.setminute.configure(background="white")
		self.exit.configure(background="grey")
	elif SET_COUNTER == 0:
		self.setday.configure(background="grey")
		self.setminute.configure(background="white")
		self.sethour.configure(background="white")
		self.exit.configure(background="white")

def destroyMainMenu(self):
	"""Destroys the main menu upon entering other menus"""
	if self.mainMenu.winfo_exists() == 1:
		self.mainMenu.destroy()

def makeSetDayMenu(self):
	"""Sets up the label widgets for the set day menu"""

	self.setDayMenu = Frame(self.myConstrainer1)
	self.setDayMenu.configure(height=20, width=390)
	self.setDayMenu.place(x=25, y= 150)

	self.monday = Label(self.setDayMenu, text = "Monday")
	self.monday.configure(height=1, width=8, background="grey")
	self.monday.grid(row=0, column=1)

	self.tuesday = Label(self.setDayMenu, text = "Tuesday")
	self.tuesday.configure(height=1, width=8, background="white")
	self.tuesday.grid(row=0, column=2)

	self.wednesday = Label(self.setDayMenu, text = "Wednesday")
	self.wednesday.configure(height=1, width=8, background="white")
	self.wednesday.grid(row=0, column=3)

	self.thursday = Label(self.setDayMenu, text = "Thursday")
	self.thursday.configure(height=1, width=8, background="white")
	self.thursday.grid(row=0, column=4)

	self.friday = Label(self.setDayMenu, text = "Friday")
	self.friday.configure(height=1, width=8, background="white")
	self.friday.grid(row=0, column=5)

	self.saturday = Label(self.setDayMenu, text = "Saturday")
	self.saturday.configure(height=1, width=8, background="white")
	self.saturday.grid(row=0, column=6)

	self.sunday = Label(self.setDayMenu, text = "Sunday")
	self.sunday.configure(height=1, width=8, background="white")
	self.sunday.grid(row=0, column=7)

def destroySetDayMenu(self):
	"""Destroys the set day menu upon entering other menus"""

	if self.setDayMenu.winfo_exists() == 1:
		self.setDayMenu.destroy()

def makeNumberMenu(self, number):
	"""Sets up photo label widgets upon entering the set hour and set minute menus"""
	global hours, minutes

	hours = []
	minutes = []
	self.setNumberMenu = Frame(self.myConstrainer1)
	self.setNumberMenu.configure(height=20, width=390)
	self.setNumberMenu.place(x=45, y= 130)

	if number == 1:
		for i in range(0, 12):
			self.temp= Label(self.setNumberMenu, text = str(i)+"AM")
			self.temp.grid(row=i//12, column=i%12)
			self.temp.configure(height=2, width=4, background = "white")
			hours.append(self.temp)
		for i in range(0, 12):
			self.temp= Label(self.setNumberMenu, text = str(i)+"PM")
			self.temp.grid(row=(i+12)//12, column=(i+12)%12)
			self.temp.configure(height=2, width=4, background = "white")
			hours.append(self.temp)
		hours[0].configure(background = "grey")
	elif number == 2:
		for i in range(0, 60):
			self.temp= Label(self.setNumberMenu, text = str(i))
			self.temp.grid(row=i//12, column=i%12)
			self.temp.configure(height=1, width=4, background = "white")
			minutes.append(self.temp)
		minutes[0].configure(background = "grey")


def destroyNumberMenu(self):
	"""Destroys the photo labels of the set hour and set minute menus"""
	if self.setNumberMenu.winfo_exists() == 1:
		self.setNumberMenu.destroy()

def createSoundFilenames():
	"""Loads up the sound files to be played upon selecting and entering each menu"""

	global INTRO_WAV, YOU_SELECTED_WAV, NUMBERS_WAV, AM_WAV, PRESS_AGAIN_TO_QUIT_WAV,\
    EXITING_PROGRAM_WAV, EXITING_PROGRAM_WAV_DURATION, EXIT, TMP_FILE_WAV, PM_WAV, OCLOCK_WAV,\
    SELECT_DAY_OF_THE_WEEK, DAYS_OF_WEEK_WAV, SET_HOUR, SET_MIN, SET_TIMEUNIT, \
    COUNTER, WEEK_DAYS, DAY, HOUR, MINUTE, STAGE, STAGES, SET_COUNTER, TIMEUNIT, ENTERING_MAINMENU, ENTERING_SETDAYMENU

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
	OCLOCK_WAV = nav_path + "o_clock_f.wav"
	PRESS_AGAIN_TO_QUIT_WAV = nav_path + "Press_again_to_quit_f.wav"
	ENTERING_MAINMENU = nav_path + "Entering_main_menu_Current_day_and_time_are_f.wav"
	ENTERING_SETDAYMENU = nav_path + "Entering_set_day_menu_Current_setting_is_f.wav"
	EXIT = nav_path + "exit_f.wav"
	EXITING_PROGRAM_WAV = nav_path + "Exiting_program_f.wav"
	EXITING_PROGRAM_WAV_DURATION = 1.09 # in s. 1.09 is accurate but 0.45 saves time.
	TMP_FILE_WAV = "tmp_file_p782s8u.wav" # Random filename  for output
	WEEK_DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
	SET_TIMEUNIT = ["Set Day", "Set Hour", "Set Minute", "Exit Program"] #The different stages that the main menu contains
	TIMEUNIT = "Set Day"
	COUNTER = 0
	SET_COUNTER = 0
	DAY = 0
	HOUR = 0
	MINUTE = 0
	STAGE = True #The STAGE value is true when the user is in the main menu, and is false in every other menu
	STAGES = [SELECT_DAY_OF_THE_WEEK, SET_HOUR, SET_MIN, EXIT]

def forward(self, stage):
	"""called by the forwardKey function; at every stage, it moves the current state of the program one value to the right.
	i.e. if the user is in the set day menu and the current day is Monday, this function moves the day to Tuesday, or if 
	it is in the set hour menu and the selected hour is 10, after calling this function will make that value 11
	"""
	global STAGE, STAGES, COUNTER, SET_COUNTER, TIMEUNIT, SET_TIMEUNIT, hours, minutes

	if stage:
		# in the main menu

		if self.mainMenu.winfo_exists() == 0:
			raise Exception("Error Occured in forward(): MainMenu Didn't Show")

		# set_counter keeps the track of different items on the main menu
		if SET_COUNTER == 0:
			SET_COUNTER +=1
			self.setday.configure(background="white")
			self.sethour.configure(background="grey")
		elif SET_COUNTER == 1:
			SET_COUNTER +=1
			self.sethour.configure(background="white")
			self.setminute.configure(background="grey")
		elif SET_COUNTER == 2:
			SET_COUNTER +=1
			self.setminute.configure(background="white")
			self.exit.configure(background="grey")
		elif SET_COUNTER == 3:
			SET_COUNTER =0
			self.setday.configure(background="grey")
			self.exit.configure(background="white")
		TIMEUNIT = SET_TIMEUNIT[SET_COUNTER]
		return TIMEUNIT

	elif stage == False:
		# when not in the main menu
		if TIMEUNIT == "Set Day":
			# in the set day menu

			if self.setDayMenu.winfo_exists() == 0:
				raise Exception("Error Occured in forward(): SetDayMenu Didn't Show")
			# counter of different days of the week
			if COUNTER == 6:
				COUNTER = 0
				self.monday.configure(background="grey")
				self.sunday.configure(background="white")
			elif COUNTER == 5:
				COUNTER += 1
				self.saturday.configure(background="white")
				self.sunday.configure(background="grey")
			elif COUNTER == 4:
				COUNTER += 1
				self.friday.configure(background="white")
				self.saturday.configure(background="grey")
			elif COUNTER == 3:
				COUNTER += 1
				self.thursday.configure(background="white")
				self.friday.configure(background="grey")
			elif COUNTER == 2:
				COUNTER += 1
				self.wednesday.configure(background="white")
				self.thursday.configure(background="grey")
			elif COUNTER == 1:
				COUNTER += 1
				self.tuesday.configure(background="white")
				self.wednesday.configure(background="grey")
			elif COUNTER == 0:
				COUNTER += 1
				self.monday.configure(background="white")
				self.tuesday.configure(background="grey")

			sound.Play(DAYS_OF_WEEK_WAV[COUNTER])
			return WEEK_DAYS[COUNTER]
		elif TIMEUNIT == "Set Hour":
			#in the set hour menu
			#counter of the different hours of the day
			if COUNTER == 23:
				COUNTER = 0
			else:
				COUNTER += 1
			hours[COUNTER].configure(background="grey")
			hours[COUNTER-1].configure(background="white")
			sound.Play(NUMBERS_WAV[COUNTER])
			return COUNTER
		elif TIMEUNIT == "Set Minute":
			#in the set minute menu
			#counter of the differnt minutes in an hour
			if COUNTER == 59:
				COUNTER = 0
			else:
				COUNTER += 1
			minutes[COUNTER].configure(background="grey")
			minutes[COUNTER-1].configure(background="white")
			sound.Play(NUMBERS_WAV[COUNTER])
			return COUNTER
		elif TIMEUNIT == "Exit Program":
			return COUNTER
		else:
			raise Exception("Error Occured in forward(): Unexpected Vlaue in TIMEUNIT")	
	else:
		raise Exception("Error Occoured in forward()")

	

def back(self, stage):
	"""called by the backKey function; at every stage, it moves the current state of the program one value to the left.
	i.e. if the user is in the set day menu and the current day is Tuesday, this function moves the day to Monday, or if 
	it is in the set hour menu and the selected hour is 10, after calling this function will make that value 9
	"""
	global STAGE, STAGES, COUNTER, SET_COUNTER, TIMEUNIT, SET_TIMEUNIT
	if stage:
		#in the main menu

		if self.mainMenu.winfo_exists() == 0:
			raise Exception("Error Occured in back(): MainMenu Didn't Show")

		# set_counter keeps the track of different items on the main menu
		if SET_COUNTER == 0:
			SET_COUNTER = 3
			self.setday.configure(background="white")
			self.exit.configure(background="grey")
		elif SET_COUNTER ==3:
			SET_COUNTER -= 1
			self.setminute.configure(background="grey")
			self.exit.configure(background="white")
		elif SET_COUNTER == 2:
			SET_COUNTER -=1
			self.setminute.configure(background="white")
			self.sethour.configure(background="grey")
		elif SET_COUNTER ==1:
			SET_COUNTER -= 1
			self.setday.configure(background="grey")
			self.sethour.configure(background="white")
		TIMEUNIT = SET_TIMEUNIT[SET_COUNTER]
		return TIMEUNIT
	elif stage == False:
		#No longer in the main menu

		if TIMEUNIT == "Set Day":
			# In the set day menu
			if self.setDayMenu.winfo_exists() == 0:
				raise Exception("Error Occured in back(): SetDayMenu Didn't Show")

			#keeps the count of the different days of the day
			if COUNTER == 0:
				COUNTER = 6
				self.monday.configure(background="white")
				self.sunday.configure(background="grey")
			elif COUNTER == 5:
				COUNTER -= 1
				self.friday.configure(background="grey")
				self.saturday.configure(background="white")
			elif COUNTER == 4:
				COUNTER -= 1
				self.thursday.configure(background="grey")
				self.friday.configure(background="white")
			elif COUNTER == 3:
				COUNTER -= 1
				self.wednesday.configure(background="grey")
				self.thursday.configure(background="white")
			elif COUNTER == 2:
				COUNTER -= 1
				self.tuesday.configure(background="grey")
				self.wednesday.configure(background="white")
			elif COUNTER == 1:
				COUNTER -= 1
				self.monday.configure(background="grey")
				self.tuesday.configure(background="white")
			elif COUNTER == 6:
				COUNTER -= 1
				self.saturday.configure(background="grey")
				self.sunday.configure(background="white")
			sound.Play(DAYS_OF_WEEK_WAV[COUNTER])
			return WEEK_DAYS[COUNTER]
		elif TIMEUNIT == "Set Hour":
			# In the set hour menu
			#	counter keeps the count of hours
			if COUNTER == 0:
				COUNTER = 23
				hours[COUNTER].configure(background="grey")
				hours[0].configure(background="white")
			else:
				COUNTER -= 1 
				hours[COUNTER].configure(background="grey")
				hours[COUNTER+1].configure(background="white")
			sound.Play(NUMBERS_WAV[COUNTER])
			return COUNTER
		elif TIMEUNIT == "Set Minute":
			# In the set minute menu
			#	counter keeps the count of minutes
			if COUNTER == 0:
				COUNTER = 59
				minutes[COUNTER].configure(background="grey")
				minutes[0].configure(background="white")
			else:
				COUNTER -= 1
				minutes[COUNTER].configure(background="grey")
				minutes[COUNTER+1].configure(background="white")
			sound.Play(NUMBERS_WAV[COUNTER])
			return COUNTER
		elif TIMEUNIT == "Exit Program":
			return COUNTER
		else:
			raise Exception("Error Occured in back(): Unexpected Vlaue in TIMEUNIT")	
	else:
		raise Exception("Error Occoured in back()")


def set(self):
	global SET_TIMEUNIT, COUNTER, DAY, HOUR, MINUTE, STAGE, SET_COUNTER, TIMEUNIT, WEEK_DAYS, EXITING_PROGRAM_WAV,ENTERING_SETDAYMENU, ENTERING_MAINMENU, WEEK_DAYS
	TEMP = COUNTER
	COUNTER = 0
	if STAGE:
		# Transitioning from the main menu to a different menu

		if SET_COUNTER == 0:
			# main menu to set day menu
			sound.combine_wav_files(TMP_FILE_WAV, ENTERING_SETDAYMENU, DAYS_OF_WEEK_WAV[DAY])
			sound.Play(TMP_FILE_WAV)
			self.timeLabel.configure(text="Monday")
			self.currentTime.configure(text="Entering Set Day menu. Current setting is: "+ WEEK_DAYS[DAY])
			destroyMainMenu(self) #removes the main menu frame
			makeSetDayMenu(self) #shows the set day frame

		elif SET_COUNTER == 1:
			# main menu to set hour
			sound.Play(STAGES[SET_COUNTER])
			self.timeLabel.configure(text="0")
			if HOUR < 12:
				self.currentTime.configure(text="Entering "+SET_TIMEUNIT[SET_COUNTER]+" menu. Current setting is: " + str(HOUR).zfill(2)+ " AM")
			if HOUR > 12:
				self.currentTime.configure(text="Entering "+SET_TIMEUNIT[SET_COUNTER]+" menu. Current setting is: " + str(HOUR-12).zfill(2) + " PM")
			destroyMainMenu(self) #removes the main menu frame
			makeNumberMenu(self, 1) #shows the set hour(numbers) widgets

		elif SET_COUNTER == 2:
			# main menu to set minute
			sound.Play(STAGES[SET_COUNTER])
			self.timeLabel.configure(text="0")
			self.currentTime.configure(text="Entering "+SET_TIMEUNIT[SET_COUNTER]+" menu. Current setting is: "+ str(MINUTE).zfill(2))
			destroyMainMenu(self) #removes the main menu frame 
			makeNumberMenu(self, 2) #shows the set minute(numbers) widgets

		elif SET_COUNTER == 3:
			# main menu to exit, exits the program
			sound.Play(EXITING_PROGRAM_WAV)
			time.sleep(EXITING_PROGRAM_WAV_DURATION)
			self.myParent.destroy() #destroys the parent frame (all the widgets) and essentially exits the program
			sys.exit()
		else:
			raise Exception("Error Occured in set(): Unexpected Value in SET_COUNTER")
		
		TIMEUNIT = SET_TIMEUNIT[SET_COUNTER]
		STAGE = False #user no longer is in the main menu
		
		return SET_TIMEUNIT[SET_COUNTER]

	elif STAGE == False:
		# transitioning back to the main menu

		if TIMEUNIT == "Set Day":
			# From set day to main menu

			DAY = TEMP
			if HOUR < 12:
				sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR], NUMBERS_WAV[MINUTE], AM_WAV)
			else:
				sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR-12], NUMBERS_WAV[MINUTE], PM_WAV)
			sound.Play(TMP_FILE_WAV)
			STAGE = True
			if HOUR < 12:
				self.currentTime.configure(text="Entering Main Menu. Current day and time are: " + WEEK_DAYS[DAY] + " " + str(HOUR).zfill(2) + ":"+ str(MINUTE).zfill(2)+ " AM")
			else:
				self.currentTime.configure(text="Entering Main Menu. Current day and time are: " + WEEK_DAYS[DAY] + " " + str(HOUR-12).zfill(2) + ":"+ str(MINUTE).zfill(2)+ " PM")
			self.timeLabel.configure(text=TIMEUNIT)
			destroySetDayMenu(self)
			makeMainMenu(self)
			return WEEK_DAYS[DAY]
		elif TIMEUNIT == "Set Hour":
			# From set hour to main menu

			HOUR = TEMP
			if HOUR < 12:
				sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR], NUMBERS_WAV[MINUTE], AM_WAV)
			else:
				sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR-12], NUMBERS_WAV[MINUTE], PM_WAV)
			sound.Play(TMP_FILE_WAV)
			STAGE = True
			if HOUR < 12:
				self.currentTime.configure(text="Entering Main Menu. Current day and time are: " + WEEK_DAYS[DAY] + " " + str(HOUR).zfill(2) + ":"+ str(MINUTE).zfill(2)+ " AM")
			else:
				self.currentTime.configure(text="Entering Main Menu. Current day and time are: " + WEEK_DAYS[DAY] + " " + str(HOUR-12).zfill(2) + ":"+ str(MINUTE).zfill(2)+ " PM")
			self.timeLabel.configure(text=TIMEUNIT)
			destroyNumberMenu(self)
			makeMainMenu(self)
			return HOUR

		elif TIMEUNIT == "Set Minute":
			# From set minute to main menu

			MINUTE = TEMP
			if HOUR < 12:
				sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR], NUMBERS_WAV[MINUTE], AM_WAV)
			else:
				sound.combine_wav_files(TMP_FILE_WAV, ENTERING_MAINMENU, DAYS_OF_WEEK_WAV[DAY], NUMBERS_WAV[HOUR-12], NUMBERS_WAV[MINUTE], PM_WAV)
			sound.Play(TMP_FILE_WAV)
			STAGE = True
			if HOUR < 12:
				self.currentTime.configure(text="Entering Main Menu. Current day and time are: " + WEEK_DAYS[DAY] + " " + str(HOUR).zfill(2) + ":"+ str(MINUTE).zfill(2)+ " AM")
			else:
				self.currentTime.configure(text="Entering Main Menu. Current day and time are: " + WEEK_DAYS[DAY] + " " + str(HOUR-12).zfill(2) + ":"+ str(MINUTE).zfill(2)+ " PM")
			self.timeLabel.configure(text=TIMEUNIT)
			destroyNumberMenu(self)
			makeMainMenu(self)
			return MINUTE
		else:
			raise Exception("Error Occured in set(). Unexpected Value in TIMEUNIT.")
	else:
		raise Exception("Error Occured in set(). Unexpected Value in STAGE.")


root = Tk()
root.geometry('600x400')
root.title('Time Scheduler')
myapp = TimeScheduler(root)
root.mainloop()
