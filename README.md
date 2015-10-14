# airfield
Self-contained Software Deployment Tool

airfield is a self-contained auto-update utility written in Java 8. 

With airfield you can easily push you applications to a remote GIT repository and automatically distribute new versions to all connected Java clients.

At the very first start airfield will clone the remote repository, all subsequent launches will update the app. You only have to push your app to whatever GIT repo you like:

[![Sample Workflow](https://i.ytimg.com/vi/mEMzM8B1E74/mqdefault.jpg)](https://www.youtube.com/embed/mEMzM8B1E74?rel=0)

##Usage:
```
java -jar airfield.jar [LOCAL_INSTALLATION_DIR] [REMOTE_GIT_REPO]
```

airfield was motivated by many requests from the [afterburner.fx](https://github.com/AdamBien/afterburner.fx) project.
