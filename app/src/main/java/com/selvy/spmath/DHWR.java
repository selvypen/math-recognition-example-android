/*!
 *  @mainpage SelvyPen Math APIs Document
 *
 *  @section intro_sec Introduction
 *  본 문서는 SELVAS AI의 수식인식 솔루션인 SelvyPen Math HWR(HandWriting Recognition)의 API 가이드이다. \n
 *  SelvyPen Math HWR(DHWR)은 손으로 필기한 글씨를 인식하여 Digital Text로 변경해 주는 솔루션이다. \n
 *  표준 C언어 기반의 라이브러리로 Android, iOS, Tizen, Windows, Linux 등의 플랫폼을 지원하며, 
 *  그 외 플랫폼 및 Non-OS는 커스터마이징을 통해 지원이 가능하다.
 *
 *  @section install_sec Setting
 *  @subsection event_sec Touch Event
 *  필기 입력기 구현을 위해서는 다음과 같은 터치 이벤트 사양이 요구된다.
 *    - Touch Down : 펜을 터치 디바이스상에 처음 대는 순간 발생하는 이벤트.
 *    - Touch Move : 펜을 터치 디바이스에서 떼지 않은 상태로 있을 경우 발생하는 이벤트.
 *    - Touch Up : 펜을 터치 디바이스 상에서 떼는 순간 발생하는 이벤트. Touch Down이 발생한 경우라면 항상 Touch Up이 발생하여야 한다. 즉, 사용자가 필기 도중 터치 패드 영역을 벗어난 경우에도 드라이버에서는 Touch Up 이벤트를 발생 시켜야 한다.
 *
 *  @subsection samplingrate_sec Sampling Rate
 *  'Sampling Rate’란 터치 디바이스에서 터치 이벤트를 체크하는 시간(or 양)을 말한다. \n
 *  필기 입력기 적용을 위해서는 보통 10~40mSec(초당 25~100개의 터치이벤트) 정도로 조정한다. \n
 *  유의할 점은 Sampling Rate가 40mSec를 넘게 되면 필기한 모양이 자연스럽지 못하고 인식률도 떨어지게 된다. 대개의 경우 10~20mSec를 권장한다.
 *
 *  @subsection delaytime_sec Delay time
 *  인식 대기 시간은 필기 입력이 끝났다고 판단하는데 걸리는 시간이다. \n
 *  즉, 터치 디바이스에서 펜을 뗀 후 일정 시간 동안 필기 입력이 없으면 사용자의 입력이 끝났다고 판단하고 인식 엔진을 호출하게 된다. \n
 *  인식 대기 시간은 설정 메뉴를 두어 사용자로 하여 적당한 값을 선택할 수 있도록 할 수도 있으며, 일반적으로 300~500mSec의 값을 사용한다. \n
 *  인식 대상 문자를 지정하는 경우 대상 문자의 종류에 따라 차등 적용할 수도 있다.(예, 한글 - 400mSec, 영숫자 - 200mSec) \n
 *  물론, 인식 대기 시간을 활용하지 않고 특정 버튼을 두어 시간에 관계없이 버튼을 클릭하면 바로 인식 엔진을 호출하도록 구현할 수도 있다.
 *
 *  @section porting_sec Porting
 *  Android 플랫폼에서 Java로 수식인식을 사용하려면, 포팅에 필요한 파일들은 다음과 같다. \n
 *  그 외 플랫폼이나 다른 언어로 개발할 경우, 파일명이나 확장자는 다를 수 있다.
 *    - DHWR.java : 필기 인식에 사용되는 API가 정의되어 있다.
 *    - libspmath.so, libspmath-base.so, libspmath-core.so : 수식인식 엔진 Main 라이브러리
 *    - xxxx.hdb : 각 언어의 인식을 위한 리소스 파일, 파일의 path를 SetExternalResourcePath()의 파라미터로 설정해야 한다.
 *    - dhwr.key : License를 위한 key 파일로써, key파일이 없으면 수식인식 엔진은 동작하지 않는다. 파일명을 포함한 path를 Create()의 파라미터로 설정해야 한다
 *
 *  @section link_sec Link
 *  SELVAS AI http://www.selvasai.com/
 */

/*!
 *  @file  DHWR.java
 *  @date  2016/06/27
 *  @author  sjko
 *
 *  Copyright (c) 2018 by SELVAS AI Inc. All rights reserved.
 */

/*! 
 *  @example example.java
 *  DHWR API를 이용하여 Android에서 수식인식을 사용하는 예제\n
 *  손글씨로 필기한 알파벳 'a'와 한글 '안녕하세요'의 좌표 값을 입력으로 받아 문자로 인식한 후 그 결과를 로그로 출력하도록 구현한 소스이다.
 */

package com.selvy.spmath;

import android.util.Log;
import java.util.ArrayList;

public class DHWR {
    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("spmath-base");
        System.loadLibrary("spmath-core");
        System.loadLibrary("spmath");
    }

    public final static String TAG = "SELVASAI";
    public final static String VERSION = "";

	public final static int MAX_CANDIDATES = 10;

    /**
     * @defgroup  PublicStaticAttributes Public Static Attributes
     * @brief     API 사용 시 파리미터나 리턴값으로 사용 가능한 상수값들이 정의되어 있다.
     * @details     @ref ErrorCode "Error Code"는 API에서 리턴 가능한 값들이 정의되어 있다. 
     * @ref LanguageMode "Language Mode", @ref LanguageType "Language Type", @ref RecognitionMode "Recognition Mode"는 인식할 대상 언어와 인식 방식을 설정할 수 있는 값들이 정의되어 있다.
     */

    /*@{*/
    /*@}*/

    //--------- Error Code ---------//    
    /**
     * @defgroup  ErrorCode Error Code
     * @ingroup   PublicStaticAttributes
     * @brief     API에서 리턴 가능한 값들이 정의되어 있다.
     * @details     Create() 호출 시 dhwr.key 파일의 path를 지정하지 않으면, @ref #ERR_AUTHORIZATION_FAIL "ERR_AUTHORIZATION_FAIL"가 리턴 된다.
     */

    /*@{*/
    //! 성공
    public final static int ERR_SUCCESS = 0x00000000;
    //! 인식결과가 없음
    public final static int ERR_NORESULT = 0x00000001;
    //! Null Pointer를 참조함
    public final static int ERR_NULL_POINTER = 0x00000002;
    //! memory access 범위를 벗어남 (fatal error)
    public final static int ERR_OUTOFMEMORY = 0x00000003;
    //! value or size 범위를 벗어남 (exception)
    public final static int ERR_OUTOFRANGE = 0x00000004;
    //! 입력된 데이터가 없음
    public final static int ERR_EMPTY_INK = 0x00000005;
    //! 입력된 인자가 잘못됨
    public final static int ERR_INVALID_ARGUMENTS = 0x00000006;
    //! 설정된 인식모델이 비정상적임
    public final static int ERR_INVALID_MODEL = 0x00000007;
    //! 잘못된 객체에 접근
    public final static int ERR_INVALID_INSTANCE = 0x00000008;
    //! 데모 기간/횟수 만료
    public final static int ERR_EXPIRE_DEMO = 0x00000009;
    //! 엔진이 실행 중
    public final static int ERR_ENGINE_BUSY = 0x0000000a;
    //! 인증 실패
    public final static int ERR_AUTHORIZATION_FAIL = 0x0000000b;
    //! 엔진 인스턴스가 이미 존재함
    public final static int ERR_ALREADY_EXIST = 0x0000000c;
    /*@}*/

    // --------- Language Mode ---------//
    /**
     * @defgroup  LanguageMode Language Mode
     * @ingroup   PublicStaticAttributes
     * @brief     수식인식이 가능한 언어 목록이 정의되어 있다.
     * @details     AddLanguage()를 호출함으로써, 인식할 언어를 추가할 수 있다. 한번에 하나의 언어만 인식이 가능하다.
     */

    /*@{*/
    // Math
    //! 초등수학
    public final static int DLANG_MATH_ELEMENTARY = 201;
    //! 중등수학
    public final static int DLANG_MATH_MIDDLE = 202;
    //! 중등수학 확장
    public final static int DLANG_MATH_MIDDLE_EXPANSION = 203;
    //! 화학식
    public final static int DLANG_MATH_CHEMICAL = 204;
    /*@}*/

    // --------- Language Type ---------//
    /**
     * @defgroup  LanguageType Language Type
     * @ingroup   PublicStaticAttributes
     * @brief     인식할 언어의 상세 설정을 위해서 사용할 값이 정의되어 있다.
     * @details     예를 들어, @ref LanguageMode "Language Mode"가 영어(#DLANG_ENGLISH)일 경우, 대문자만 인식하거나, 소문자만 인식하거나, 또는 숫자만 인식하게 하는 등의 설정이 가능하다.\n
     * - Example usage:
     * @code
     * // 초등수학만 인식
     * DHWR.AddLanguage(mSetting.GetHandle(), DHWR.DLANG_MATH_ELEMENTARY, DHWR.DTYPE_NONE);
     * @endcode
     * - @ref LanguageType "Language Type"을 기호(#DTYPE_SIGN)로 설정할 경우, 인식 가능한 기호 목록은 다음과 같다.
     |Hex Code|Character|
     |------------|-----------|
     |0021 ~ 002F|! " # $ % & ' ( ) * + , - . /|
     |003A ~ 0040|: ; < = > ? @|
     |005B ~ 0060|[ \\ ] ^ _ `|
     |007B ~ 007E|{ \| } ~|
     */

    /*@{*/
    //! 타입 없음

    public final static int DTYPE_NONE        = 0;
    public final static int DTYPE_MATH_ET     = (1 << 22);
    public final static int DTYPE_MATH_MD     = (1 << 23);
    public final static int DTYPE_MATH_EX     = (1 << 24);
    public final static int DTYPE_MATH_CF     = (1 << 25);
    /*@}*/

    // --------- Param type ---------//
    /*!
     *  @brief Param Type
     */
    public final static int DHWR_LOG_LEVEL = 0;
    public final static int DHWR_LOG_CALLBACK = 1;
    public final static int DHWR_WRITE_INK = 2;
    public final static int DHWR_LANG_MODEL = 3;

    // --------- log level ---------//
    /*!
     *  @brief Log Level
     */
    public final static int LEVEL_NONE = 0;
    public final static int LEVEL_ERROR = 1;
    public final static int LEVEL_WARN = 2;
    public final static int LEVEL_INFO = 3;
    public final static int LEVEL_DEBUG = 4;

    // --------- recognition mode ---------//
    /**
     * @defgroup  RecognitionMode Recognition Mode
     * @ingroup   PublicStaticAttributes
     * @brief     낱자로 쓰여진 글자를 인식할지, 여러 글자를 인식할지, 또는 여러 줄로 된 글자를 인식할지 설정하는데 사용된다.
     * @details     SetRecognitionMode()의 두번째 파라미터로 사용한다.
     */

    /*@{*/
    //! 낱자 인식
    public final static int SINGLECHAR = 0;
    //! 여러 글자 인식
    public final static int MULTICHAR = 1;
    //! 겹쳐 쓴 글자 인식
    public final static int OVERLAPCHAR = 2;
    //! 여러 줄로 된 글자 인식
    public final static int MULTILINE = 3;
    /*@}*/

    // --------- Ink Inner Class ---------//
    public static class Point {
        public int x = 0;
        public int y = 0;
    }

    public static class Ink {
        public Ink() {
            mId = CreateInkObject();
        }

        public Ink(long id) {
            mId = id;
        }

        @Override
        protected void finalize() throws Throwable {
            DHWR.DestroyInkObject(mId);
            super.finalize();
        }

        public boolean AddPoint(int x, int y) {
            return DHWR.AddPoint(mId, x, y);
        }

        public boolean EndStroke() {
            return DHWR.EndStroke(mId);
        }

        public void Clear() {
            DHWR.InkClear(mId);
        }

        public boolean GetPoint(int index, Point point) {
            return DHWR.GetInkPoint(mId, index, point);
        }

        public int GetSize() {
            return DHWR.GetInkCount(mId);
        }

        public long GetHandle() {
            return mId;
        }
        private long mId;
    }

    // --------- Setting Inner Class ---------//
    public static class Setting {
        public Setting() {
            mId = CreateSettingObject();
        }

        @Override
        protected void finalize() throws Throwable {
            DHWR.DestroySettingObject(mId);
            super.finalize();
        }

        public int SetMode(int mode) {
            return DHWR.SetRecognitionMode(mId, mode);
        }

        public int SetCandidateSize(int size) {
            return DHWR.SetCandidateSize(mId, size);
        }

        public int AddLanguage(int lang, int option) {
            return DHWR.AddLanguage(mId, lang, option);
        }

        public int GetLanguageSize() {
            return DHWR.GetLanguageSize(mId);
        }

        public int ClearLanguage() {
            return DHWR.ClearLanguage(mId);
        }

        public int SetUserCharSet(char[] charset) {
            return DHWR.SetUserCharSet(mId, charset);
        }

        public long GetHandle() {
            return mId;
        }

        private long mId;
    }

    // --------- Result Inner Class ---------//
    public static class BlockInfo {
        public int stroke_count;
        public int[] stroke_indices;
    }

    public static class Block {
        public ArrayList<String> candidates = new ArrayList<String>();
        public BlockInfo info;
    }

    public static class Line extends ArrayList<Block> {
        private static final long serialVersionUID = -3254237493961808528L;
    }

    public static class Result extends ArrayList<Line> {
        private static final long serialVersionUID = -318179353348774962L;
    }

    public DHWR() {}

    /***************************************************************
     * Main Raw APIs
     ***************************************************************/
    /**
     * @defgroup  Main Main APIs
     * @brief     엔진 사용에 필수적인 함수
     * @details     Create()를 호출하여 인식엔진을 생성한 후에 사용이 끝나면, 반드시 Close()를 호출하여 인식엔진을 소멸시켜야 한다.
     */

    /*@{*/
    /*!
     *  @brief 인식엔진 세션을 시작함
     *  @param [in] key License key 파일의 경로
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int Create(String key);

    /*!
     *  @brief 인식엔진 세션을 마침
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int Close();

    /*!
     *  @brief 인식모드 속성 값에 따라 입력 데이터를 인식함
     *  @param [in] ink 좌표가 입력된 Ink Object의 handle
     *  @param [out] result 인식된 결과가 저장된 결과 오브젝트의 handle
     *  @return @ref ErrorCode "Error Code"
     *  @see InkObject, ResultObject
     */
    public final static native int RecognizeWithContext(long ink, long result);

    /*!
     *  @brief 인식모드 속성 값에 따라 입력 데이터를 인식함\n
     *           RecognizeWithContext()와 동일한 기능을 하며, RecognizeWithContext()를 사용하기 쉽도록 wrapping한 함수이다.
     *  @param [in] ink 좌표가 입력된 Ink Object
     *  @param [out] result 인식된 결과가 저장된 결과 오브젝트
     *  @return @ref ErrorCode "Error Code"
     *  @see InkObject, ResultObject
     */
    public final static int Recognize(Ink ink, Result result) {
        result.clear();
        long handle_result = DHWR.CreateResultObject();
        int ret = DHWR.RecognizeWithContext(ink.GetHandle(), handle_result);
        long size_line = DHWR.GetLineSize(handle_result);
        for (int i = 0; i < size_line; i++) {
            Line line = new Line();
            long handle_line = DHWR.GetLine(handle_result, i);
            int size_block = DHWR.GetBlockSize(handle_line);
            for (int j = 0; j < size_block; j++) {
                Block block = new Block();
                long handle_block = DHWR.GetBlock(handle_line, j);
                BlockInfo info = new BlockInfo();
                int size_stroke = DHWR.GetStrokeSize(handle_block);
                info.stroke_count = size_stroke;
                info.stroke_indices = new int[size_stroke];
                DHWR.GetStrokeIndices(handle_block, info.stroke_indices, size_stroke);
                block.info = info;
                PrintLog(DHWR.LEVEL_DEBUG, TAG, "DHWR.java Stroke count : " + info.stroke_count);
                for (int k = 0; k < size_stroke; k++) {
                    PrintLog(DHWR.LEVEL_DEBUG, TAG, "DHWR.java Stroke ["+k+"] : " + info.stroke_indices[k]);
                }

                int size_candidates = DHWR.GetCandidateSize(handle_block);
                for (int k = 0; k < size_candidates; k++) {
                    String text = DHWR.GetCandidate(handle_block, k);
                    String strHex = "";
                    char a;
                    for (int l = 0; l < text.length(); l++) {
                        a = text.charAt(l);
                        strHex += "0x"+Integer.toHexString((int) (a & 0xFFFF));
                        if (l != text.length() - 1)
                            strHex += " ";
                    }
                    String stLog = text + "(" + strHex + ")";

                    PrintLog(DHWR.LEVEL_DEBUG, TAG, "DHWR.java [" + i + "," + j + "," + k + "]: \"" + stLog + "\"");
                    block.candidates.add(text);
                }
                if (block.candidates.isEmpty() == false) {
                    line.add(block);
                }
            }
            if (line.isEmpty() == false) {
                result.add(line);
            }
        }
        DHWR.DestroyResultObject(handle_result);
        return ret;
    }
    /*@}*/

    /***************************************************************
     * Ink Raw APIs
     ***************************************************************/
    /**
     * @defgroup  InkObject InkObject APIs
     * @ingroup   Main
     * @brief     좌표를 관리하는 InkObject관련 함수
     * @details     CreateInkObject()로 생성된 오브젝트는 반드시 DestroyInkObject()를 호출하여 소멸시켜야 한다.\n
     * AddPoint()로 필기 좌표 값을 입력하고, Touch Up 시점에서는 EndStroke()를 호출하여 하나의 획이 끝난 것을 인식엔진에 알려줘야 한다.\n
     * InkObject APIs는 일반적으로 다음과 같은 순서로 호출하게 된다.
     * -# 잉크 오브젝트 생성 : CreateInkObject()
     * -# 잉크 오브젝트 초기화 : InkClear()
     * -# 좌표 값을 잉크 오브젝트에 추가 : AddPoint(), AddPoint(),... EndStroke(), AddPoint(),... EndStroke(), AddPoint(), AddPoint(),... EndStroke()
     * -# 잉크를 글자로 인식 : Recognize()
     * -# 잉크 오브젝트 제거 : DestroyInkObject()
     */

    /*@{*/
    /*!
     *  @brief 잉크 오브젝트를 생성한다
     *  @return 잉크 오브젝트의 handle
     */
    public final static native long CreateInkObject();

    /*!
     *  @brief 잉크 오브젝트를 제거한다
     *  @param [in] ink 잉크 오브젝트의 handle
     *  @return void
     */
    public final static native void DestroyInkObject(long ink);

    /*!
     *  @brief 인식엔진에서 인식할 터치 좌표(잉크 좌표)를 잉크 오브젝트에 추가함
     *  @param [in] ink 잉크 오브젝트의 handle
     *  @param [in] x x 좌표 값
     *  @param [in] y y 좌표 값
     *  @return 터치 좌표가 성공적으로 추가되면 true, 아니면 false를 반환한다
     */
    public final static native boolean AddPoint(long ink, int x, int y);

    /*!
     *  @brief AddPoint()를 호출하여 잉크 오브젝트에 터치 좌표 값을 추가하다가, Touch-Up 이벤트에 의해 한 획의 입력을 마치면 호출한다.
     *  @param [in] ink 잉크 오브젝트
     *  @return 한 획의 입력이 성공적으로 마쳤으면 true, 아니면 false를 반환한다
     */
    public final static native boolean EndStroke(long ink);

    /*!
     *  @brief 입력한 좌표 데이터를 초기화함
     *  @param [in] ink 잉크 오브젝트
     *  @return void
     */
    public final static native void InkClear(long ink);

    /*!
     *  @brief 입력된 잉크 index 값에 해당하는 잉크 좌표값을 받아옴
     *  @param [in] ink 잉크 오브젝트
     *  @param [in] index 받아오고자 하는 잉크의 index
     *  @param [out] point 해당 잉크의 x, y좌표 값
     *  @return 좌표값을 성공적으로 받아오면 true, 아니면 false를 반환한다
     */
    public final static native boolean GetInkPoint(long ink, int index, Point point);

    /*!
     *  @brief 현재까지 입력된 잉크의 카운트 값을 반환하다.\n
     *           잉크의 카운트 값이란, AddPoint()를 호출하여 추가된 (x, y) 좌표 값의 개수를 의미한다. AddPoint()를 호출한 횟수로 봐도 된다.
     *  @param [in] ink 잉크 오브젝트
     *  @return 잉크 카운트
     */
    public final static native int GetInkCount(long ink);
    /*@}*/

    /***************************************************************
     * Setting Raw APIs
     ***************************************************************/
    /**
     * @defgroup SettingObject SettingObject APIs
     * @ingroup  Main
     * @brief    엔진 동작을 결정하는 SettingObject관련 함수
     * @details     인식할 언어, 인식모드 등을 설정할 수 있는 API를 제공한다. SettingObject APIs는 일반적으로 다음과 같은 순서로 호출하게 된다.
     * -# 설정 오브젝트 생성 : CreateSettingObject()
     * -# 인식모드 설정 (낱자 인식/여러 글자 인식/여러 줄 인식) : SetRecognitionMode()
     * -# 인식할 언어 설정 : AddLanguage()
     * -# 최대 후보문자 개수 설정 : SetCandidateSize()
     * -# 설정 오브젝트를 인식에 사용하도록 설정 : SetAttribute()
     * -# 문자 인식 : Recognize()
     * -# 설정 오브젝트 제거 : DestroySettingObject()
     * @warning CreateSettingObject()로 생성된 오브젝트는 반드시 DestroySettingObject()를 호출하여 소멸시켜야 한다.
     */

    /*@{*/
    /*!
     *  @brief 인식모드 속성 값을 설정함
     *  @param [in] setting 설정이 완료된 Setting Object의 handle
     *  @return @ref ErrorCode "Error Code"
     *  @see SettingObject
     */
    public final static native int SetAttribute(long setting);

    /*!
     *  @brief 설정 오브젝트를 생성한다
     *  @return 설정 오브젝트의 handle
     */
    public final static native long CreateSettingObject();

    /*!
     *  @brief 설정 오브젝트를 제거한다
     *  @param [in] setting 설정 오브젝트의 handle
     *  @return void
     */
    public final static native void DestroySettingObject(long setting);

    /*!
     *  @brief 필기 인식 모드를 설정한다.(낱자 인식/여러 글자 인식/여러 줄 인식)
     *  @param [in] setting 설정 오브젝트의 handle
     *  @param [in] mode @ref RecognitionMode "인식 모드"
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int SetRecognitionMode(long setting, int mode);

    /*!
     *  @brief 인식 후보 출력 크기를 설정한다.
     *           10으로 설정할 경우, 후보가 최대 10개까지 생성될 수 있다는 의미이다. 반드시 10개가 되는 것은 아니다.
     *  @param [in] setting 설정 오브젝트의 handle
     *  @param [in] sizeCand 후보수
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int SetCandidateSize(long setting, int sizeCand);

    /*!
     *  @brief 인식할 언어를 추가 한다
     *  @param [in] setting 설정 오브젝트의 handle
     *  @param [in] lang 언어 값 (@ref LanguageMode "Language Mode")
     *  @param [in] option 언어에 따른 옵션 (@ref LanguageType "Language Type")
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int AddLanguage(long setting, int lang, int option);

    /*!
     *  @brief 설정된 언어 크기를 가져온다
     *  @param [in] setting 설정 오브젝트의 handle
     *  @return 언어 크기
     */
    public final static native int GetLanguageSize(long setting);

    /*!
     *  @brief 설정된 언어를 초기화 한다
     *  @param [in] setting 설정 오브젝트의 handle
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int ClearLanguage(long setting);

    /*!
     *  @brief 인식할 기호의 목록을 설정하면, 설정된 기호들만 인식하고, 그 외의 기호들은 무시할 수 있다.
     *  @param [in] setting 설정 오브젝트의 handle
     *  @param [in] charset 인식할 기호의 리스트\n
     *                            기호는 @ref LanguageType "Language Type" 챕터에서 표에 정의된 기호만 사용 가능하다.
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int SetUserCharSet(long setting, char[] charset);
    /*@}*/

    /***************************************************************
     * Result Raw APIs
     ***************************************************************/
    /**
     * @defgroup ResultObject ResultObject APIs
     * @ingroup  Main
     * @brief    인식된 결과를 저장하는 ResultObject 관련 함수
     * @details     CreateResultObject()로 생성된 오브젝트는 반드시 DestroyResultObject()를 호출하여 소멸시켜야 한다.
     * - 블럭 오브젝트 : 블럭 오브젝트는 문자열 리스트이다.
     * - 라인 오브젝트 : 라인 오브젝트는 블럭 오브젝트의 리스트이다.
     * - 결과 오브젝트 : 결과 오브젝트는 라인 오브젝트의 리스트이다.
     * - 각 오브젝트의 관계는 다음의 예시를 통해 확인할 수 있다.
     * -# @e "hello" 한 단어를 필기 했다면, 다음과 같이 결과 오브젝트에 인식 결과가 저장된다.\n
     * 사용자의 필기에 따라 후보 문자와 개수는 달라질 수 있다.
     * 1순위 후보가 best 후보가 된다.
     * @code
     * Result = { Line[0] }
     *     Line[0] = { Block[0] }
     *         Block[0] = { "hello", "hell", ... } // 1순위 후보: hello, 2순위 후보: hell, 3순위 후보: ...
     * @endcode
     * -# @e "hello world" 한 문장을 필기 했다면, 다음과 같이 결과 오브젝트에 인식 결과가 저장된다.
     * @code
     * Result = { Line[0] }
     *     Line[0] = { Block[0], Block[1] }
     *         Block[0] = { "hello", "hell", ... }
     *         Block[1] = { "world", "word", ... }
     * @endcode
     * -# @e "hello world" @e "good morning" 두 문장을 두 줄로 필기 했다면, 다음과 같이 결과 오브젝트에 인식 결과가 저장된다.
     * @code
     * Result = { Line[0], Line[1] }
     *     Line[0] = { Block[0], Block[1] }
     *         Block[0] = { "hello", "hell", ... }
     *         Block[1] = { "world", "word", ... }
     *     Line[1] = { Block[0], Block[1] }
     *         Block[0] = { "good", "good", ... }
     *         Block[1] = { "morning", "morming", ... }
     * @endcode
     */

    /*@{*/
    /*!
     *  @brief 결과 오브젝트를 생성한다
     *  @return 결과 오브젝트의 handle
     */
    public final static native long CreateResultObject();

    /*!
     *  @brief 결과 오브젝트 내의 라인 오브젝트 개수를 가져온다
     *  @param [in] context 결과 오브젝트의 handle
     *  @return 라인 오브젝트 개수
     */
    public final static native int GetLineSize(long context);

    /*!
     *  @brief 결과 오브젝트로부터 지정된 index의 라인 오브젝트 handle을 가져온다
     *  @param [in] context 결과 오브젝트의 handle
     *  @param [in] index 라인 오브젝트의 index
     *  @return 라인 오브젝트의 handle
     */
    public final static native long GetLine(long context, int index);

    /*!
     *  @brief 라인 오브젝트 내의 블럭 오브젝트 개수를 가져온다
     *  @param [in] line 라인 오브젝트의 handle
     *  @return 블럭 오브젝트 개수
     */
    public final static native int GetBlockSize(long line);

    /*!
     *  @brief 라인 오브젝트로부터 지정된 index의 블럭 오브젝트 handle을 가져온다
     *  @param [in] line 라인 오브젝트의 handle
     *  @param [in] index 블럭 오브젝트의 index
     *  @return 블럭 오브젝트의 handle
     */
    public final static native long GetBlock(long line, int index);

    /*!
     *  @brief 블럭 오브젝트 내 후보 문자열을 구성한 획의 개수를 얻어온다
     *  @param [in] block 블럭 오브젝트의 handle
     *  @return 획의 개수
     */
    public final static native int GetStrokeSize(long block);

    /*!
     *  @brief 블럭 오브젝트 내 후보 문자열을 구성한 획의 index들을 얻어온다
     *  @param [in] block 블럭 오브젝트의 handle
     *  @param [out] indices index Array
     *  @param [in] size index Array 사이즈
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int GetStrokeIndices(long block, int[] indices, int size);

    /*!
     *  @brief 블럭 오브젝트로부터 후보 크기를 가져온다.
     *  @param [in] block 블럭 오브젝트의 handle
     *  @return Candidate Size
     */
    public final static native int GetCandidateSize(long block);

    /*!
     *  @brief 블럭 오브젝트로부터 인식된 후보 문자열을 가져온다.
     *  @param [in] block 블럭 오브젝트의 handle
     *  @param [in] index 후보 index
     *  @return 인식 후보 문자열
     */
    public final static native String GetCandidate(long block, int index);

    /*!
     *  @brief 결과 오브젝트를 제거한다
     *  @param [in] context 결과 오브젝트의 handle
     *  @return void
     */
    public final static native void DestroyResultObject(long context);
    /*@}*/

    /***************************************************************
     * Optional APIs
     ***************************************************************/
    /**
     * @defgroup Optional Optional APIs
     */

    /*@{*/
    /*!
     *  @brief 사용 가능한 언어 목록을 반환한다
     *  @return 언어 목록 문자열
     */
    public final static native String AvailableLanguageList();

    /*!
     *  @brief 엔진내부에서 사용하는 외부라이브러리의 경로 설정
     *  @warning Create()를 실행한 이후에 호출해야 한다
     *  @param [in] path 외부라이브러리의 경로
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int SetExternalLibraryPath(char[] path);

    /*!
     *  @brief 엔진내부에서 사용하는 외부리소스(언어모델 파일 등)의 경로 설정
     *  @warning Create()를 실행한 이후에 호출해야 한다
     *  @param [in] path 리소스 경로
     *  @return @ref ErrorCode "Error Code"
     *  @par &nbsp;&nbsp;&nbsp;example
     *  @code
     *  DHWR.SetExternalResourcePath("/data/data/package-name/hdb/".toCharArray());
     *  @endcode
     */
    public final static native int SetExternalResourcePath(char[] path);

    /*!
     *  @brief 특정 타입의 파라미터 값을 가져옴
     *  @param [in] type 파라미터 타입
     *  @param [out] param 특정 파리미터 타입의 값
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int SetParam(int type, byte[] param);

    /*!
     *  @brief 특정 타입의 파라미터 값을 설정함
     *  @param [in] type 파라미터 타입
     *  @param [in] param 특정 파리미터 타입의 값
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int GetParam(int type, byte[] param);

    /*!
     *  @brief 엔진의 빌드넘버를 가져옴
     *  @param [out] revision 엔진 빌드넘버
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int GetRevision(char[] revision);

    /*!
     *  @brief License key에 설정된 엔진의 due date를 가져온다
     *  @remarks License key의 due date가 존재하지 않을 경우 due date는 초기 값 0이다
     *  @param [out] dueDate License key의 due date, 년월일을 나타내는 8자리의 integer 값 (ex 20161206)
     *  @return @ref ErrorCode "Error Code"
     */
    public final static native int GetDueDate(int[] dueDate);

    /***************************************************************
     * Debug Helper functions
     ***************************************************************/
    /*!
     *  @brief 로그 출력을 활성화 한다
     *  @return @ref ErrorCode "Error Code"
     */
    public static int SetLogEnable() {
        byte[] level = new byte[8];
        level[0] = LEVEL_DEBUG;
        return SetParam(DHWR.DHWR_LOG_LEVEL, level);
    }

    /*!
     *  @brief 로그 출력을 비활성화 한다
     *  @return @ref ErrorCode "Error Code"
     */
    public static int SetLogDisable() {
        byte[] level = new byte[8];
        level[0] = LEVEL_NONE;
        return SetParam(DHWR.DHWR_LOG_LEVEL, level);
    }

    /*!
     *  @brief 로그 출력 레벨을 반환한다
     *  @return 로그 출력 레벨
     */
    public final static byte GetLogLevel() {
        byte[] level = new byte[8];
        DHWR.GetParam(DHWR.DHWR_LOG_LEVEL, level);

        return level[0];
    }

    /*!
     *  @brief 로그를 출력한다
     *  @param [in] logLevel 로그 레벨
     *  @param [in] tag TAG 문자열
     *  @param [in] log 출력할 로그 문자열
     *  @return void
     */
    public final static void PrintLog(int logLevel, String tag, String log) {
        if (logLevel <= GetLogLevel()) {
            switch(logLevel) {
            case DHWR.LEVEL_ERROR:
                Log.e(tag, log);
                break;
            case DHWR.LEVEL_DEBUG:
                Log.d(tag, log);
                break;
            case DHWR.LEVEL_INFO:
                Log.i(tag, log);
                break;
            };
        }
    }
    /*@}*/
}
