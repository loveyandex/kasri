const { app, BrowserWindow ,globalShortcut} = require('electron')

let win;

//***//

function createWindow () {
  // Create the browser window.
  win = new BrowserWindow({
    width: 1000,
    height: 750,
    backgroundColor: '#9cd1ad'
  });

  // win.loadURL(`file://${__dirname}/dist/index.html`)
  // win.loadURL(`https://earth.nullschool.net`)
  win.loadURL(`http://tv.aut.ac.ir/tv3`)


  //// uncomment below to open the DevTools.
  // win.webContents.openDevTools()

  // Event when the window is closed.
  win.on('closed', function () {
    win = null
  });

    // Register a 'CommandOrControl+X' shortcut listener.
    const ret = globalShortcut.register('F5', () => {
        win.reload();
    });
    // Register a 'CommandOrControl+X' shortcut listener.
    const ret1 = globalShortcut.register('F11', () => {
       if (win.isFullScreen())
         win.setFullScreen(false);
       else
           win.setFullScreen(true);

    })


}

// Create window on electron intialization
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function () {

  // On macOS specific close process
  if (process.platform !== 'darwin') {
    app.quit()
  }
});

app.on('activate', function () {
  // macOS specific close process
  if (win === null) {
    createWindow()
  }
});
