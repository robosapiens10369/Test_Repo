package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class MecanumHardware
{
    /* Public OpMode members. */
    public DcMotor  leftFront  = null;
    public DcMotor  rightFront  = null;
    public DcMotor  rightBack  = null;
    public DcMotor  leftBack  = null;

    // Variables for Motion Methods
    public double MotionPwr = 0;
    public double MotionTime = 0;

    // Extra motors added to the 2nd Expansion Hub
    public DcMotor  ActuatorMotor = null;
    public DcMotor  ElbowMotor = null;
    public DcMotor  HeightMotor = null;


    // Servos controlling the arm
    public Servo    Servo0         = null;
    public Servo    Servo1         = null;
    public Servo    Servo2         = null;
    public Servo    Servo3         = null;

    // Since both servos work as one, they share same position
    public final static double Servo_Close = 0;
    public final static double Servo_Open = 1;

    // Color Sensor
    public ColorSensor FrontColorSensor;
    public OpticalDistanceSensor FrontDistanceSensor;

    // Arm Variables
    public final static double Extra = 0.1;



    /*
    public final static double ARM_MIN_RANGE  = 0.20;
    public final static double ARM_MAX_RANGE  = 0.90;
    public final static double CLAW_MIN_RANGE  = 0.20;
    public final static double CLAW_MAX_RANGE  = 0.7;
    */

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MecanumHardware() {
    }

    public void StrafeLf(double MovePwr){
        leftFront.setPower(MovePwr);
        leftBack.setPower(-MovePwr);
        rightFront.setPower(-MovePwr);
        rightBack.setPower(MovePwr);
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFront = hwMap.dcMotor.get("LeftFront");
        leftBack = hwMap.dcMotor.get("LeftBack");
        rightFront = hwMap.dcMotor.get("RightFront");
        rightBack = hwMap.dcMotor.get("RightBack");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        // Arm Motors
        ActuatorMotor = hwMap.dcMotor.get("ExtendMotor");
        ElbowMotor = hwMap.dcMotor.get("ElbowMotor");
        HeightMotor = hwMap.dcMotor.get("HeightMotor");



        // Color and Distance Sensor
        FrontColorSensor = hwMap.colorSensor.get("Color0");
        FrontDistanceSensor = hwMap.opticalDistanceSensor.get("Color0");


        /*
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        */

       // Define and initialize ALL installed servos.
        Servo0 = hwMap.get(Servo.class, "Servo0");
        Servo1 = hwMap.get(Servo.class, "Servo1");
        Servo2 = hwMap.get(Servo.class, "Servo2");
        Servo3 = hwMap.get(Servo.class, "Servo3");
        Servo0.setPosition(Servo_Open);
        Servo1.setPosition(Servo_Open);



    }
}


