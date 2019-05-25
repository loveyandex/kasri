@IF EXIST "..\nodejs\node.exe" (
  "..\nodejs\node.exe"  "..\nodejs\node_modules\http-server\bin\http-server" %*
) ELSE (
  @SETLOCAL
  @SET PATHEXT=%PATHEXT:;.JS;=;%
  node  "..\nodejs\node_modules\http-server\bin\http-server" %*
)