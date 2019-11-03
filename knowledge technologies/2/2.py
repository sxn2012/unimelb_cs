

def main():
    f_in = open("test.csv", "r")
    line = f_in.readline().split("\n")[0]
    dict=[]
    while line:
        temp=line.split(",")
        t=[]
        t.append(temp[0])
        t.append(temp[1])
        dict.append(t)
        line = f_in.readline().split("\n")[0]
    f_in.close()

    f_res = open("knn.csv", "r")
    line = f_res.readline().split("\n")[0]
    res = []
    while line:
        temp = line.split(",")
        t = []
        t.append(temp[0])
        t.append(temp[1])
        res.append(t)
        line = f_res.readline().split("\n")[0]
    f_res.close()

    f_out=open("out.csv","w")
    for i in dict:
        for j in res:
            if(i[1]==j[0]):
                f_out.writelines(i[0]+","+j[1]+"\n")
                break
    f_out.close()



    return




if __name__=="__main__":
    main()
