import mysql.connector
import sys
from mysql.connector import Error

def retrieve_student_info(sid):
    try:
        # 连接到MySQL数据库
        connection = mysql.connector.connect(
            host='boldwinds.top',
            database='cgi',
            user='test',
            password='123456'
        )

        if connection.is_connected():
            # 创建游标对象
            cursor = connection.cursor()

            # 执行查询
            query = "SELECT sid, name, class FROM student_tbl WHERE sid = %s"
            cursor.execute(query, (sid,))

            # 检索结果
            result = cursor.fetchone()

            # 输出结果
            print("<html>\n<p>\n")
            if result:
                sid, name, class_ = result
                print(f"学生信息：SID={sid}, 姓名={name}, 班级={class_}")
            else:
                print ("Student not found\n");
            print("</p>\n</html>\n");

    except Error as e:
        print("连接到MySQL服务器时发生错误：", e)

    finally:
        # 关闭数据库连接
        if connection and connection.is_connected():
            cursor.close()
            connection.close()

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Please provide two numbers as arguments.")
    else:
        # 在这里调用函数并传递SID参数
        retrieve_student_info(sys.argv[1])
