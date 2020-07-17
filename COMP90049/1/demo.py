from nltk import *
from ngram import *
from threading import *
import re
dict=[]#list of dictionary
cand=[]#list of candidates
ans=[]#list of correct answer
threadlock=Lock()#control the output
tp=0#true positive
fp=0#false positive
tn=0#true negative
fn=0#false negative
dict_contain_A = []
dict_contain_B = []
dict_contain_C = []
dict_contain_D = []
dict_contain_E = []
dict_contain_F = []
dict_contain_G = []
dict_contain_H = []
dict_contain_I = []
dict_contain_J = []
dict_contain_K = []
dict_contain_L = []
dict_contain_M = []
dict_contain_N = []
dict_contain_O = []
dict_contain_P = []
dict_contain_Q = []
dict_contain_R = []
dict_contain_S = []
dict_contain_T = []
dict_contain_U = []
dict_contain_V = []
dict_contain_W = []
dict_contain_X = []
dict_contain_Y = []
dict_contain_Z = []
def readdata():
    f_dict = open("dict.txt", "r")
    line = f_dict.readline().split("\n")[0]

    while line:

        dict.append(line)


        line = f_dict.readline().split("\n")[0]

    f_dict.close()
    f_candidate = open("candidates.txt","r")
    line = f_candidate.readline().split("\n")[0]

    while line:

        cand.append(line)


        line = f_candidate.readline().split("\n")[0]

    f_candidate.close()
    f_answer = open("1.txt","r")
    line = f_answer.readline().split("\n")[0]

    while line:

        ans.append(line)


        line = f_answer.readline().split("\n")[0]

    f_answer.close()
    return



def initialize():
    global dict_contain_A 
    global dict_contain_B 
    global dict_contain_C 
    global dict_contain_D 
    global dict_contain_E 
    global dict_contain_F 
    global dict_contain_G 
    global dict_contain_H 
    global dict_contain_I 
    global dict_contain_J 
    global dict_contain_K 
    global dict_contain_L 
    global dict_contain_M 
    global dict_contain_N 
    global dict_contain_O 
    global dict_contain_P 
    global dict_contain_Q 
    global dict_contain_R 
    global dict_contain_S 
    global dict_contain_T 
    global dict_contain_U 
    global dict_contain_V 
    global dict_contain_W 
    global dict_contain_X 
    global dict_contain_Y 
    global dict_contain_Z 
    for str_ in dict:
        if (re.search(r'^a', str_, re.I) or re.search(r'a$', str_, re.I)):
            dict_contain_A.append(str_)
        elif (re.search(r'^b', str_, re.I) or re.search(r'b$', str_, re.I)):
            dict_contain_B.append(str_)
        elif (re.search(r'^c', str_, re.I) or re.search(r'c$', str_, re.I)):
            dict_contain_C.append(str_)
        elif (re.search(r'^d', str_, re.I) or re.search(r'd$', str_, re.I)):
            dict_contain_D.append(str_)
        elif (re.search(r'^e', str_, re.I) or re.search(r'e$', str_, re.I)):
            dict_contain_E.append(str_)
        elif (re.search(r'^f', str_, re.I) or re.search(r'f$', str_, re.I)):
            dict_contain_F.append(str_)
        elif (re.search(r'^g', str_, re.I) or re.search(r'g$', str_, re.I)):
            dict_contain_G.append(str_)
        elif (re.search(r'^h', str_, re.I) or re.search(r'h$', str_, re.I)):
            dict_contain_H.append(str_)
        elif (re.search(r'^i', str_, re.I) or re.search(r'i$', str_, re.I)):
            dict_contain_I.append(str_)
        elif (re.search(r'^j', str_, re.I) or re.search(r'j$', str_, re.I)):
            dict_contain_J.append(str_)
        elif (re.search(r'^k', str_, re.I) or re.search(r'k$', str_, re.I)):
            dict_contain_K.append(str_)
        elif (re.search(r'^l', str_, re.I) or re.search(r'l$', str_, re.I)):
            dict_contain_L.append(str_)
        elif (re.search(r'^m', str_, re.I) or re.search(r'm$', str_, re.I)):
            dict_contain_M.append(str_)
        elif (re.search(r'^n', str_, re.I) or re.search(r'n$', str_, re.I)):
            dict_contain_N.append(str_)
        elif (re.search(r'^o', str_, re.I) or re.search(r'o$', str_, re.I)):
            dict_contain_O.append(str_)
        elif (re.search(r'^p', str_, re.I) or re.search(r'p$', str_, re.I)):
            dict_contain_P.append(str_)
        elif (re.search(r'^q', str_, re.I) or re.search(r'q$', str_, re.I)):
            dict_contain_Q.append(str_)
        elif (re.search(r'^r', str_, re.I) or re.search(r'r$', str_, re.I)):
            dict_contain_R.append(str_)
        elif (re.search(r'^s', str_, re.I) or re.search(r's$', str_, re.I)):
            dict_contain_S.append(str_)
        elif (re.search(r'^t', str_, re.I) or re.search(r't$', str_, re.I)):
            dict_contain_T.append(str_)
        elif (re.search(r'^u', str_, re.I) or re.search(r'u$', str_, re.I)):
            dict_contain_U.append(str_)
        elif (re.search(r'^v', str_, re.I) or re.search(r'v$', str_, re.I)):
            dict_contain_V.append(str_)
        elif (re.search(r'^w', str_, re.I) or re.search(r'w$', str_, re.I)):
            dict_contain_W.append(str_)
        elif (re.search(r'^x', str_, re.I) or re.search(r'x$', str_, re.I)):
            dict_contain_X.append(str_)
        elif (re.search(r'^y', str_, re.I) or re.search(r'y$', str_, re.I)):
            dict_contain_Y.append(str_)
        elif (re.search(r'^z', str_, re.I) or re.search(r'z$', str_, re.I)):
            dict_contain_Z.append(str_)
    return

class DataThread(Thread):
    def __init__(self,ind):
        Thread.__init__(self)
        self.loc=ind#record the location in the list
        self.i=cand[ind]
        return
    
    def run(self):
        global tp
        global fp
        global tn
        global fn
        trial_ac=""
        trial_pr=""
        trial_re=""

        
        while True:
            poss = []#possible components
            flag=False
            temp_1 = []
            temp_2 = []
            if (re.search(r'^a', self.i, re.I) or re.search(r'a$', self.i, re.I)):
                temp_1 = dict_contain_A
            elif (re.search(r'^b', self.i, re.I)):
                temp_1 = dict_contain_B
            elif (re.search(r'^c', self.i, re.I)):
                temp_1 = dict_contain_C
            elif (re.search(r'^d', self.i, re.I)):
                temp_1 = dict_contain_D
            elif (re.search(r'^e', self.i, re.I)):
                temp_1 = dict_contain_E
            elif (re.search(r'^f', self.i, re.I)):
                temp_1 = dict_contain_F
            elif (re.search(r'^g', self.i, re.I)):
                temp_1 = dict_contain_G
            elif (re.search(r'^h', self.i, re.I)):
                temp_1 = dict_contain_H
            elif (re.search(r'^i', self.i, re.I)):
                temp_1 = dict_contain_I
            elif (re.search(r'^j', self.i, re.I)):
                temp_1 = dict_contain_J
            elif (re.search(r'^k', self.i, re.I)):
                temp_1 = dict_contain_K
            elif (re.search(r'^l', self.i, re.I)):
                temp_1 = dict_contain_L
            elif (re.search(r'^m', self.i, re.I)):
                temp_1 = dict_contain_M
            elif (re.search(r'^n', self.i, re.I)):
                temp_1 = dict_contain_N
            elif (re.search(r'^o', self.i, re.I)):
                temp_1 = dict_contain_O
            elif (re.search(r'^p', self.i, re.I)):
                temp_1 = dict_contain_P
            elif (re.search(r'^q', self.i, re.I)):
                temp_1 = dict_contain_Q
            elif (re.search(r'^r', self.i, re.I)):
                temp_1 = dict_contain_R
            elif (re.search(r'^s', self.i, re.I)):
                temp_1 = dict_contain_S
            elif (re.search(r'^t', self.i, re.I)):
                temp_1 = dict_contain_T
            elif (re.search(r'^u', self.i, re.I)):
                temp_1 = dict_contain_U
            elif (re.search(r'^v', self.i, re.I)):
                temp_1 = dict_contain_V
            elif (re.search(r'^w', self.i, re.I)):
                temp_1 = dict_contain_W
            elif (re.search(r'^x', self.i, re.I)):
                temp_1 = dict_contain_X
            elif (re.search(r'^y', self.i, re.I)):
                temp_1 = dict_contain_Y
            elif (re.search(r'^z', self.i, re.I)):
                temp_1 = dict_contain_Z
            
            if (re.search(r'a$', self.i, re.I)):
                temp_2 = dict_contain_A
            elif (re.search(r'b$', self.i, re.I)):
                temp_2 = dict_contain_B
            elif (re.search(r'c$', self.i, re.I)):
                temp_2 = dict_contain_C
            elif (re.search(r'd$', self.i, re.I)):
                temp_2 = dict_contain_D
            elif (re.search(r'e$', self.i, re.I)):
                temp_2 = dict_contain_E
            elif (re.search(r'f$', self.i, re.I)):
                temp_2 = dict_contain_F
            elif (re.search(r'g$', self.i, re.I)):
                temp_2 = dict_contain_G
            elif (re.search(r'h$', self.i, re.I)):
                temp_2 = dict_contain_H
            elif (re.search(r'i$', self.i, re.I)):
                temp_2 = dict_contain_I
            elif (re.search(r'j$', self.i, re.I)):
                temp_2 = dict_contain_J
            elif (re.search(r'k$', self.i, re.I)):
                temp_2 = dict_contain_K
            elif (re.search(r'l$', self.i, re.I)):
                temp_2 = dict_contain_L
            elif (re.search(r'm$', self.i, re.I)):
                temp_2 = dict_contain_M
            elif (re.search(r'n$', self.i, re.I)):
                temp_2 = dict_contain_N
            elif (re.search(r'o$', self.i, re.I)):
                temp_2 = dict_contain_O
            elif (re.search(r'p$', self.i, re.I)):
                temp_2 = dict_contain_P
            elif (re.search(r'q$', self.i, re.I)):
                temp_2 = dict_contain_Q
            elif (re.search(r'r$', self.i, re.I)):
                temp_2 = dict_contain_R
            elif (re.search(r's$', self.i, re.I)):
                temp_2 = dict_contain_S
            elif (re.search(r't$', self.i, re.I)):
                temp_2 = dict_contain_T
            elif (re.search(r'u$', self.i, re.I)):
                temp_2 = dict_contain_U
            elif (re.search(r'v$', self.i, re.I)):
                temp_2 = dict_contain_V
            elif (re.search(r'w$', self.i, re.I)):
                temp_2 = dict_contain_W
            elif (re.search(r'x$', self.i, re.I)):
                temp_2 = dict_contain_X
            elif (re.search(r'y$', self.i, re.I)):
                temp_2 = dict_contain_Y
            elif (re.search(r'z$', self.i, re.I)):
                temp_2 = dict_contain_Z
            if temp_1!=temp_2:
                temp_=temp_1+temp_2
            else:
                temp_=temp_1
            for j in temp_:
                sim = NGram.compare(self.i, j, N=2)
                if sim >= 0.5:
                    flag=True
                    break

            if flag==False:
                if self.i in ans:
                    fn = fn + 1
                else:
                    tn = tn + 1
                threadlock.acquire()
                print(str(self.loc) + ":" + self.i + " is not a blend!")
                threadlock.release()
            else:
                if self.i in ans:
                    tp = tp + 1
                else:
                    fp = fp + 1
                threadlock.acquire()

                print(str(self.loc) + ":" + self.i + " is a blend!")
                threadlock.release()

            if tp+fp+tn+fn!=0:
                trial_ac=str((tp+tn)/(tp+fp+tn+fn))
            else:
                trial_ac="N/A"
            if tp+fp!=0:
                trial_pr=str(tp/(tp+fp))
            else:
                trial_pr="N/A"
            if tp+fn!=0:
                trial_re=str(tp/(tp+fn))
            else:
                trial_re="N/A"
            threadlock.acquire()
            print("result of No."+str(self.loc)+":"+"accuracy:"+trial_ac+", precision:"+trial_pr+", recall:"+trial_re+" [tp:"+str(tp)+", tn:"+str(tn)+", fp:"+str(fp)+", fn:"+str(fn)+"]")
            threadlock.release()
            self.loc=self.loc+10
            if self.loc>=len(cand):
                break
            else:
                self.i=cand[self.loc]
        return


def main():
    readdata()
    initialize()
    print("initialization complete!")
    for i in range(0,10):
        threadi = DataThread(i)
        threadi.start()


    return

if __name__=="__main__":
    main()