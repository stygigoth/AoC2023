global _start

section .text

_start:
  pop rdi
  pop rdi
  pop rdi

  mov rax, 2
  mov rsi, 0
  syscall

  mov rdi, rax       
  mov rsi, buf
  mov rdx, 1024*1024
  mov rax, 0
  syscall

  mov rdi, 1
  mov rsi, buf
  mov rdx, rax
  mov rax, 1
  syscall            

  mov rax, 60
  mov rdi, 0
  syscall

section .bss
  buf: resb 1024*1024
