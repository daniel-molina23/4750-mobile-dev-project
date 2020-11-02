# 4750-mobile-dev-project
This repo is meant to store our project generated from the CS 4750 class.

If you are adding a new feature, make sure to use a seperate branch. Branches are used so we don't interfere with each other's code. It allows us to work simultaneously on different things and avoids collisions.

Once tests are ran and it all works well we can create a pull request to the master branch.

If you have any questions at all dont hesitate to ask the discord!


Step 1:
------------
Install git
https://git-scm.com/downloads

Step 2:
------------
after setting up development environment, create a file from IDE, check working directory "C:/user/..." and go to terminal and "cd" (change directory) to the general folder where the IDE saves changes, not to a specific project. Confirm it is the right directory with "pwd" (print working directory). Now, instantiate the git repo only once with keywords in "git init". This will instatiate a general repo for all your dev stuff.

Step 3:
------------
now that is done, go ahead and clone this private repo locally on your machine. "git clone (link-here)"



Helpful Tips:
------------
- anytime you want to see changes that were made to see them locally, use the "git pull"
- anytime you want to switch branch use "git checkout (branch-name)"
- to check changes you have made, use the "git status"
- to check which branch you are on use "git branch"
- try to never make changes directly to master to ensure application is working properly at all times
- create a branch with "git checkout -b (branch-name)" or just use the GUI github has to make a branch easier
- when you are ready to commit changes:
    * open git and go to the local repo directory with "cd (path)"
    * ensure you are on the proper branch "git branch"
    * "git status" to see the changes you made are correct
    * "git add ." or "git add ClassName.java" first one adds all changes, second adds only a single change
    * "git commit -m "updated KotlinClassName.class and fixed bugs"" to commit with a message  so the description can help us understand better
    * "git push"
    * and done!


