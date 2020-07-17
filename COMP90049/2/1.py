

def main():
    f_in = open("train.csv", "r")
    line = f_in.readline().split("\n")[0]
    dict=[]
    while line:
        temp=line.split(",")
        dict.append(temp)
        line = f_in.readline().split("\n")[0]
    f_in.close()
    #print(dict)
    res=[]
    t=[]
    f_out = open("trainres.csv", "w")
    for i in dict:
        if i[1] in t:
            continue
        temp=i

        for j in dict:
            if((i[0]!=j[0]) and (i[1]==j[1])):
                for s in range(2,len(temp)-1):
                    temp[s]=str(int(temp[s])+int(j[s]))
                    #print(temp)
        str_=""
        for k in temp:
            str_=str_+k+","
        str_=str_[0:len(str_)-1]
        res.append(str_)
        t.append(i[1])
        print(str_)
        f_out.writelines(str_+"\n")
    
    f_out.close()


    return




if __name__=="__main__":
    main()
