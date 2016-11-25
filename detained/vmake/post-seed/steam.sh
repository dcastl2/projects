#!/bin/sh

### BEGIN INIT INFO
# Provides:       postseed
# Required-Start: $local_fs $remote_fs
# Required-Stop:
# X-Start-Before:
# Default-Start:  2 3 4 5
# Default-Stop:
### END INIT INFO

update-rc.d postseed  remove
