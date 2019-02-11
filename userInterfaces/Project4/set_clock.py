__author__ = "Kaiyu Song"

from tkinter import *
import sound


class MyUI:
    def __init__(self, root):
        self.myRoot = root
        root.bind("<j>", self.forward)
        root.bind("<k>", self.backward)
        root.bind("<space>", self.select)
        root.title("Set day and time")
        self.Frame1 = Frame(root)
        self.Frame1.grid(row=0, column=1)
        self.Frame2 = Frame(root)
        self.Frame2.grid(row=0, column=2)
        self.Frame3 = Frame(root)
        self.Frame3.grid(row=0, column=3)
        self.FrameD = Frame(root)
        self.FrameD.grid(row=1, column=1)
        self.FrameH = Frame(root)
        self.FrameH.grid(row=1, column=2)
        self.FrameM = Frame(root)
        self.FrameM.grid(row=1, column=3)
        self.FrameNote = Frame(root)
        self.FrameNote.grid(row=2, column=1)
        self.FrameNote.config(bg="yellow")
        self.FrameShow = Frame(root)
        self.FrameShow.grid(row=2, column=2)
        self.FrameE = Frame(root)
        self.FrameE.grid(row=2, column=3)
        self.showtime = Label(self.FrameShow, font=("Helvetica", 30, "italic"), text="")
        self.showtime.pack()
        self.location = [0, 0]
        self.memory = [[0, 0], [1, 0], [2, 0], [3, 0]]
        self.UIlist = []
        self.sounds = []
        self.specialsounds = []
        self.buildui()
        self.makesounds()
        self.update()

    def buildui(self):
        labeld = Label(self.Frame1, text="Set Day",
                       font=("Helvetica", 20))
        labeld.grid(row=0, column=0)
        labelh = Label(self.Frame2, text="Set Hour",
                       font=("Helvetica", 20))
        labelh.grid(row=0, column=1)
        labelm = Label(self.Frame3, text="Set Minute",
                       font=("Helvetica", 20))
        labelm.grid(row=0, column=2)
        labele = Label(self.FrameE, text="Exit Programing",
                       font=("Helvetica", 20))
        labele.grid(row=0, column=3)
        list0 = [labeld, labelh, labelm, labele]
        label1 = Label(self.FrameD, text="Monday")
        label1.grid(row=0, column=0)
        label2 = Label(self.FrameD, text="Tuesday")
        label2.grid(row=1, column=0)
        label3 = Label(self.FrameD, text="Wednesday")
        label3.grid(row=2, column=0)
        label4 = Label(self.FrameD, text="Thursday")
        label4.grid(row=3, column=0)
        label5 = Label(self.FrameD, text="Friday")
        label5.grid(row=4, column=0)
        label6 = Label(self.FrameD, text="Saturday")
        label6.grid(row=5, column=0)
        label7 = Label(self.FrameD, text="Sunday")
        label7.grid(row=6, column=0)
        list1 = [label1, label2, label3, label4, label5, label6, label7]
        list2 = []
        for i in range(1, 13, 1):
            label = Label(self.FrameH, text=str(i)+" AM")
            label.grid(row=(i-1)//4, column=(i-1) % 4)
            list2.append(label)
        for i in range(1, 13, 1):
            label = Label(self.FrameH, text=str(i)+" PM")
            label.grid(row=((i-1) // 4)+3, column=(i-1) % 4)
            list2.append(label)
        list3 = []
        for i in range(0, 60, 1):
            label = Label(self.FrameM, text=i)
            label.grid(row=i // 10, column=i % 10)
            list3.append(label)
        self.UIlist = [list0, list1, list2, list3]
        ln1 = Label(self.FrameNote, text="Press J as forward", bg="yellow", fg="purple")
        ln1.grid(row=0, column=0)
        ln2 = Label(self.FrameNote, text="Press K as backward", bg="yellow", fg="purple")
        ln2.grid(row=1, column=0)
        ln3 = Label(self.FrameNote, text="Press Space as select", bg="yellow", fg="purple")
        ln3.grid(row=2, column=0)
        for item in self.memory:
            self.hl(self.UIlist[item[0]][item[1]])
        self.mark(self.UIlist[0][0])

    def makesounds(self):
        mp = "wav_files_provided/miscellaneous_f/"
        np = "wav_files_provided/numbers_f/"
        dp = "wav_files_provided/days_of_week_f/"
        menulist = [mp+"Set_day_f.wav", mp+"Set_hour_f.wav",
                    mp+"Set_minute_f.wav", mp+"Exit_program_f.wav"]
        daylist = [dp+"monday_f.wav", dp+"tuesday_f.wav",
                   dp+"wednesday_f.wav", dp+"thursday_f.wav",
                   dp+"friday_f.wav", dp+"saturday_f.wav", dp+"sunday_f.wav"]
        hourlist = [np + "01_f.wav", np + "02_f.wav", np + "03_f.wav",
                    np + "04_f.wav", np + "05_f.wav", np + "06_f.wav",
                    np + "07_f.wav", np + "08_f.wav", np + "09_f.wav",
                    np + "10_f.wav", np + "11_f.wav", np + "12_f.wav",
                    np + "01_f.wav", np + "02_f.wav", np + "03_f.wav",
                    np + "04_f.wav", np + "05_f.wav", np + "06_f.wav",
                    np + "07_f.wav", np + "08_f.wav", np + "09_f.wav",
                    np + "10_f.wav", np + "11_f.wav", np + "12_f.wav"]
        minutelist = [np + "00_f.wav", np + "01_f.wav", np + "02_f.wav",
                      np + "03_f.wav", np + "04_f.wav", np + "05_f.wav",
                      np + "06_f.wav", np + "07_f.wav", np + "08_f.wav",
                      np + "09_f.wav", np + "10_f.wav", np + "11_f.wav",
                      np + "12_f.wav", np + "13_f.wav", np + "14_f.wav",
                      np + "15_f.wav", np + "16_f.wav", np + "17_f.wav",
                      np + "18_f.wav", np + "19_f.wav", np + "20_f.wav",
                      np + "21_f.wav", np + "22_f.wav", np + "23_f.wav",
                      np + "24_f.wav", np + "25_f.wav", np + "26_f.wav",
                      np + "27_f.wav", np + "28_f.wav", np + "29_f.wav",
                      np + "30_f.wav", np + "31_f.wav", np + "32_f.wav",
                      np + "33_f.wav", np + "34_f.wav", np + "35_f.wav",
                      np + "36_f.wav", np + "37_f.wav", np + "38_f.wav",
                      np + "39_f.wav", np + "40_f.wav", np + "41_f.wav",
                      np + "42_f.wav", np + "43_f.wav", np + "44_f.wav",
                      np + "45_f.wav", np + "46_f.wav", np + "47_f.wav",
                      np + "48_f.wav", np + "49_f.wav", np + "50_f.wav",
                      np + "51_f.wav", np + "52_f.wav", np + "53_f.wav",
                      np + "54_f.wav", np + "55_f.wav", np + "56_f.wav",
                      np + "57_f.wav", np + "58_f.wav", np + "59_f.wav"]
        self.sounds = [menulist, daylist, hourlist, minutelist]
        self.specialsounds = ["tmp_file_p782s8u.wav", mp+"you_selected_f.wav",
                              mp+"AM_f.wav", mp+"PM_f.wav"]
        sound.Play(self.sounds[0][0])

    def hl(self, object):
        object["bg"] = "grey"
        object["fg"] = "white"

    def ll(self, object):
        object["bg"] = "white"
        object["fg"] = "black"

    def mark(self, object):
        object["bg"] = "red"
        object["fg"] = "blue"

    def forward(self, event):
        self.ll(self.UIlist[self.location[0]][self.location[1]])
        self.location[1] += 1
        if self.location[1] == len(self.UIlist[self.location[0]]):
            self.location[1] = 0
        self.mark(self.UIlist[self.location[0]][self.location[1]])
        if self.location[0] == 2:
            if self.location[1] < 12:
                sound.combine_wav_files(self.specialsounds[0],
                                        self.sounds[2][self.location[1]],
                                        self.specialsounds[2])
                sound.Play(self.specialsounds[0])
            else:
                sound.combine_wav_files(self.specialsounds[0],
                                        self.sounds[2][self.location[1]],
                                        self.specialsounds[3])
                sound.Play(self.specialsounds[0])
        else:
            sound.Play(self.sounds[self.location[0]][self.location[1]])

    def backward(self, event):
        self.ll(self.UIlist[self.location[0]][self.location[1]])
        self.location[1] -= 1
        if self.location[1] == -1:
            self.location[1] = len(self.UIlist[self.location[0]])-1
        self.mark(self.UIlist[self.location[0]][self.location[1]])
        if self.location[0] == 2:
            if self.location[1] < 12:
                sound.combine_wav_files(self.specialsounds[0],
                                        self.sounds[2][self.location[1]],
                                        self.specialsounds[2])
                sound.Play(self.specialsounds[0])
            else:
                sound.combine_wav_files(self.specialsounds[0],
                                        self.sounds[2][self.location[1]],
                                        self.specialsounds[3])
                sound.Play(self.specialsounds[0])
        else:
            sound.Play(self.sounds[self.location[0]][self.location[1]])

    def select(self, event):
        if self.location[0] == 0:
            if self.location[1] == 3:
                self.myRoot.destroy()
            self.hl(self.UIlist[self.location[0]][self.location[1]])
            self.memory[0] = self.location
            self.location = self.memory[self.location[1]+1]
            self.mark(self.UIlist[self.location[0]][self.location[1]])
            if self.location[0] == 2:
                if self.location[1] < 12:
                    sound.combine_wav_files(self.specialsounds[0],
                                            self.sounds[2][self.location[1]],
                                            self.specialsounds[2])
                    sound.Play(self.specialsounds[0])
                else:
                    sound.combine_wav_files(self.specialsounds[0],
                                            self.sounds[2][self.location[1]],
                                            self.specialsounds[3])
                    sound.Play(self.specialsounds[0])
            else:
                sound.Play(self.sounds[self.location[0]][self.location[1]])
        else:
            self.hl(self.UIlist[self.location[0]][self.location[1]])
            self.memory[self.location[0]] = self.location
            selection = self.location
            self.location = self.memory[0]
            self.mark(self.UIlist[self.location[0]][self.location[1]])
            if selection[0] == 2:
                if selection[1] < 12:
                    sound.combine_wav_files(self.specialsounds[0],
                                            self.specialsounds[1],
                                            self.sounds[2][selection[1]],
                                            self.specialsounds[2],
                                            self.sounds[0][self.location[1]])
                else:
                    sound.combine_wav_files(self.specialsounds[0],
                                            self.specialsounds[1],
                                            self.sounds[2][selection[1]],
                                            self.specialsounds[3],
                                            self.sounds[0][self.location[1]])
                sound.Play(self.specialsounds[0])
            else:
                sound.combine_wav_files(self.specialsounds[0],
                                        self.specialsounds[1],
                                        self.sounds[selection[0]][selection[1]],
                                        self.sounds[0][self.location[1]])
                sound.Play(self.specialsounds[0])
        self.update()

    def update(self):
        start = "The Time Selected is "
        x = self.UIlist[1][self.memory[1][1]]["text"]
        y = ((self.memory[2][1])+1) % 12
        z = self.UIlist[3][self.memory[3][1]]["text"]
        if y == 0:
            y = 12
        now = " PM"
        if self.memory[2][1] < 12:
            now = " AM"
        self.showtime["text"] = start + x + " " + str(y) + " : " + str(z) + now


root = Tk()
MyUI(root)
root.mainloop()
