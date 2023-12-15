let read_file file =
  In_channel.with_open_text file In_channel.input_all
  |> String.trim
let explode s =
  List.init (String.length s) (String.get s)

let s = read_file "input"
let a = ref 0
let c = ref 0

let l = s
|> String.split_on_char ','
|> List.map (fun x -> explode x
  |> List.map (fun x -> Char.code x)
  )

let _ = l
|> List.iter (fun x -> x
    |> List.iter (fun x -> c := !c + x; c := !c * 17; c := !c mod 256;); a := !a + !c; c := 0
  )
let _ = print_endline "Part one:"
let _ = print_int !a
let _ = print_endline " "

