import os
import sys

# The script starts a command line interface that allows to execute the following commands:
# - "docker-compose up -d --build" when the user first runs the script
# - "docker-compose down" when the user presses "q" and enters the "q" command

dir_path = os.path.dirname(os.path.realpath(__file__))

def wait_for_seconds(seconds):
	# Wait for n seconds and refresh the page
	if sys.platform == "win32":
		os.system("timeout " + str(seconds))
	elif sys.platform == "darwin":
		os.system("sleep " + str(seconds))
	elif sys.platform == "linux" or sys.platform == "linux2":
		os.system("sleep " + str(seconds))

#Check if docker is running
if os.system("docker info") != 0:
	print("[ERROR] Docker is not running. Please start Docker and run the script again.")
	print("> Press Ctrl+C to exit.")
	while True:
		# press any key to exit
		pass
	sys.exit()

print("[OK] Docker is running.")
wait_for_seconds(1)

# Check if docker-compose is installed
if os.system("docker-compose --version") != 0:
	print("[ERROR] Docker-compose is not installed. Please install Docker-compose and run the script again.")
	print("> Press Ctrl+C to exit.")
	while True:
		# press any key to exit
		pass
	sys.exit()

print("[OK] Docker-compose is installed.")
wait_for_seconds(1)

# Check if docker-compose.yml exists
if not os.path.exists(dir_path + "/docker-compose.yaml") and not os.path.exists(dir_path + "/docker-compose.yml"):
	print("[ERROR] No docker-compose.yml or docker-compose.yaml file found. Please add a docker-compose.yml or docker-compose.yaml file and run the script again.")
	print("> Press Ctrl+C to exit.")
	while True:
		# press any key to exit
		pass
	sys.exit()

print("[OK] docker-compose.yml or docker-compose.yaml exists.")
wait_for_seconds(1)

# Move to the directory of the script
os.chdir(dir_path)

running_containers = os.popen("docker-compose ps").read()

# Check if the app is already running (note that the "docker-compose ps" command always prinits a row with the column names)
# Check if he printout of the command "docker-compose ps" contains the string "recipe-cove" somewhere
if "recipe-cove" in running_containers:
	print("[STOPPING APP] The app is already running. Closing the app...")

	os.system("cd " + dir_path + " && docker-compose down")

	wait_for_seconds(1)

	print("[OK] The app was closed.")

else:
	print("[STARTING APP] Starting the app...")

	# The script should be able to be executed from any directory.
	os.system("cd " + dir_path + " && docker-compose up -d --build")

	wait_for_seconds(1)

	seconds_to_wait_to_start_browser = 3

	# Open a browser at http://localhost:3000 (note that the "open" command works only on Mac)
	# Windows
	if sys.platform == "win32":
		# os.system("start https://localhost:3000")
		# Wait for n seconds and refresh the page
		os.system("timeout " + str(seconds_to_wait_to_start_browser) + " && start https://localhost:3000")
	# Mac
	elif sys.platform == "darwin":
		# os.system("open https://localhost:3000")
		# Wait for n seconds and refresh the page
		os.system("sleep " + str(seconds_to_wait_to_start_browser) + " && open https://localhost:3000")
	# Linux
	elif sys.platform == "linux" or sys.platform == "linux2":
		# os.system("xdg-open https://localhost:3000")
		# Wait for n seconds and refresh the page
		os.system("sleep " + str(seconds_to_wait_to_start_browser) + " && xdg-open https://localhost:3000")

	wait_for_seconds(1)

	print("[OK] The app is running.")
	
# Close the terminal after a delay
wait_for_seconds(1)
os.system("exit")