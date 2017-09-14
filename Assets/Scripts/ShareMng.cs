using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShareMng : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}

    // Update is called once per frame
    void Update() {
        if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Home))
        {
            Application.Quit();
        }
	}
    string debugStr="null";
    private void OnGUI()
    {
        if (GUILayout.Button("分享",GUILayout.Width(100), GUILayout.Height(50)))
        {
            using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
            {
                using (AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
                {
                    jo.Call("startGave");
                }
            }
        }
        GUILayout.Label(debugStr,GUILayout.Width(500),GUILayout.Height(100));

    }
    void SetDebugStr(string value)
    {
        debugStr += value;
    }
}
