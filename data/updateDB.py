import pymysql
import csv

#DB Select
def select_test(id):
    conn = pymysql.connect(host='k3b104.p.ssafy.io', user='root', password='rootroot', db='final')
    try:
        with conn.cursor() as curs:
            sql = "select * from video where id=%s"
            curs.execute(sql, id)
            rs = curs.fetchall()
            for row in rs:
                print(row)
    finally:
        conn.close()

#DB Update
def update_test():
    conn = pymysql.connect(host='k3b104.p.ssafy.io', user='root', password='rootroot', db='final')
    f = open('video.csv', 'r')
    rdr = csv.DictReader(f)
    # for row in rdr:
    #     print(row['id'], row['title'], row['url'])

    try:
        with conn.cursor() as curs:
            for row in rdr:
                # if int(row['id']) < 11:
                #     continue
                # if row['id'] == "11":
                #     break
                sql = "UPDATE `video` SET `url` = (%s), `title` = (%s) WHERE `id` = (%s)"
                answer = [row['url'], row['title'], row['id']]
                curs.execute(sql, answer)
                conn.commit()
                select_test(row['id'])
    finally:
        conn.close()


if __name__ == "__main__":
    update_test()