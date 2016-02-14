import random

PLACEMARKS_COUNT = 100000;


f = open('placemarks.csv', 'w')

for i in xrange(1, PLACEMARKS_COUNT):
	f.write(str(i))
	f.write(',')
	f.write(str(random.uniform(-90, 90)))
	f.write(',')
	f.write(str(random.uniform(-180, 180)))
	f.write('\n')

f.close()

f = open('mapcells.csv', 'w')

for x in xrange(-179, 179):
	for y in xrange(-89, 89):
		f.write(str(x))
		f.write(',')
		f.write(str(y))
		f.write(',')
		f.write(str(random.gauss(5000, 1000)))
		f.write('\n')

f.close()
