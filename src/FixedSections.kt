import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.pow

const val numberOfSections=5
class Section{
    var path:String=""
    var size:Long=0
    var isUsed:Boolean=false
fun uploadTask(path:String):Boolean{
    val size:Long=sizeOfFile(path)
    if(!isUsed&&size<=this.size){
        isUsed=true
        this.path=path
        return true
    }
    return false
}
    fun deleteTask(path:String):Boolean{
        if(path==this.path){
            this.path=""
            isUsed=false
            return true
        }
        return false
    }
}
val arr =arrayOfNulls<Section>(numberOfSections)
val list= mutableListOf<String>()
fun main(){
    arr[0]=Section()
    arr[0]!!.size=4096
    for(i in 1..numberOfSections-1){
        arr[i]=Section()
        arr[i]!!.size=2.0.pow(i+1).toLong()*1024
    }
    menu()
}
fun sizeOfFile(path:String):Long{
    var path = Path.of(path)
    var lol=true
    var size:Long=0
    while(lol){
    try {
        size = Files.size(path)
        lol=false
    } catch (e:NoSuchFileException){
        print("Нет такого файла. Введите путь заново!>")
        val path1:String= readLine().toString()
        path=Path.of(path1)
    }
    }
    return size
}
fun uploadTask(path:String){
    for(i in 0 until numberOfSections){
        if(arr[i]!!.uploadTask(path)) return
    }
    list.add(path)
}
fun deleteTask(path:String){
    var deleted=false
    for(i in 0 until numberOfSections){
        if(arr[i]!!.deleteTask(path)) deleted=true
    }
    if(deleted){
        for(i in 0 until list.size){
            for(j in 0 until numberOfSections){
                if(arr[j]!!.uploadTask(list[i])){
                    list.removeAt(i)
                    return
                }
            }
        }
    }
}
fun menu(){
    while (true) {
        println("1 - добавить задачу, 2 - удалить задачу, 3 - просмотр состояния моделируемой памяти, 4 - завершить выполнение программы")
        val v = readLine().toString().toInt()
        when (v) {
            1 -> {
                print("Введите путь к файлу>")
                val path = readLine().toString()
                if(sizeOfFile(path)<=32767)
                uploadTask(path)
                else println("Система слабенькая. Файл слишком тяжёлый. Поменяйте оперативку!")
            }
            2 -> {
                print("Введите путь к файлу>")
                val path = readLine().toString()
                deleteTask(path)
            }
            3->{
                for(item in arr){
                    if (item!!.isUsed){
                        println("Раздел на ${item!!.size} байт занят файлом  ${item!!.path}")
                    }
                    else println("Раздел на ${item!!.size} байт свободен")
                }
            }
            4 -> return
            else -> {
                println("Нет такого варианта!!!")
            }
        }
    }
}