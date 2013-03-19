pepa
====

PEPA Eclipse Plugin

test.pepa

f1 = 1.2;
f2 = 0.8;
f3 = 0.8;
s2 = 0.9;
w1 = 0.4;
s1 = 0.5;
p1 = 100;
s3 = 500;

Well = (sink,w1).(store_w,s1).Well;
Farm = (sow,f1).(tend,f2).(reap,f3).(store_f,s2).Farm;
Brewery = (store_w,s1).BreweryW + (store_f,s2).BreweryF;
BreweryW = (store_f,s2).BreweryP;
BreweryF = (store_w,s3).BreweryP;
BreweryP = (produce,p1).Brewery;

(Well[5] <store_w> Brewery[5])<store_f> Farm[5]
